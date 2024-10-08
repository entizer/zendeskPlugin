package com.morpheusdata.task

import com.morpheusdata.core.AbstractTaskService
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.util.HttpApiClient
import com.morpheusdata.model.*
import com.morpheusdata.response.ServiceResponse
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import com.morpheusdata.core.data.DataQuery
import com.morpheusdata.core.data.DataFilter

/**
 * Example AbstractTaskService. Each method demonstrates building an example TaskConfig for the relevant task type
 */
@Slf4j
class ZenDeskTaskServiceRequest extends AbstractTaskService {
	MorpheusContext context

    ZenDeskTaskServiceRequest(MorpheusContext context) {
		this.context = context
	}

	@Override
	MorpheusContext getMorpheus() {
		return context
	}

	@Override
	TaskResult executeLocalTask(Task task, Map opts, Container container, ComputeServer server, Instance instance) {
		TaskConfig config = buildLocalTaskConfig([:], task, [], opts).blockingGet()
		if(instance) {
			config = buildInstanceTaskConfig(instance, [:], task, [], opts).blockingGet()
		}
		if(container) {
			config = buildContainerTaskConfig(container, [:], task, [], opts).blockingGet()
		}
	
		executeTask(task, config)
	}

	@Override
	TaskResult executeServerTask(ComputeServer server, Task task, Map opts) {
		TaskConfig config = buildComputeServerTaskConfig(server, [:], task, [], opts).blockingGet()
		context.executeCommandOnServer(server, 'echo $JAVA_HOME')
		context.executeCommandOnServer(server, 'echo $JAVA_HOME', false, 'user', 'password', null, null, null, false, false)
		executeTask(task, config)
	}

	@Override
	TaskResult executeServerTask(ComputeServer server, Task task) {
		TaskConfig config = buildComputeServerTaskConfig(server, [:], task, [], [:]).blockingGet()
		context.executeCommandOnServer(server, 'echo $JAVA_HOME')
		executeTask(task, config)
	}

	@Override
	TaskResult executeContainerTask(Container container, Task task, Map opts) {
		TaskConfig config = buildContainerTaskConfig(container, [:], task, [], opts).blockingGet()
		context.executeCommandOnWorkload(container, 'echo $JAVA_HOME')
		context.executeCommandOnWorkload(container, 'echo $JAVA_HOME', 'user', 'password', null, null, null, false, null, false)
		executeTask(task, config)
	}

	@Override
	TaskResult executeContainerTask(Container container, Task task) {
		TaskConfig config = buildContainerTaskConfig(container, [:], task, [], [:]).blockingGet()
		executeTask(task, config)
	}

	@Override
	TaskResult executeRemoteTask(Task task, Map opts, Container container, ComputeServer server, Instance instance) {
		TaskConfig config = buildRemoteTaskConfig([:], task, [], opts).blockingGet()
		context.executeCommandOnWorkload(container, 'echo $JAVA_HOME')
		executeTask(task, config)
	}

	@Override
	TaskResult executeRemoteTask(Task task, Container container, ComputeServer server, Instance instance) {
		TaskConfig config = buildRemoteTaskConfig([:], task, [], [:]).blockingGet()
		context.executeSshCommand('localhost', 8080, 'bob', 'password', 'echo $JAVA_HOME', null, null, null, false, null, LogLevel.debug, false, null, true)
		executeTask(task, config)
	}

	/**
	 * Finds the input text from the OptionType created in {@link ZenDeskTaskProviderCreateRequest#getOptionTypes}.
	 * Uses Groovy {@link org.codehaus.groovy.runtime.StringGroovyMethods#reverse} on the input text
	 * @param task
	 * @param config
	 * @return data and output are the reversed text
	 */
	TaskResult executeTask(Task task, TaskConfig config) {
		// https://domain.zendesk.com
		String zenDeskTargetUrl = task.taskOptions.find { it.optionType.code == 'zenDeskTargetUrl'}?.value
		String apiPath = '/api/v2/requests'
		String zenDeskPriority = task.taskOptions.find { it.optionType.code == 'zenDeskPriority'}?.value
		String zenDeskSubject = task.taskOptions.find { it.optionType.code == 'zenDeskSubject'}?.value
		String zenDeskMessageDetails = task.taskOptions.find { it.optionType.code == 'zenDeskMessageDetails'}?.value

//		String data = "zenDeskTargetUrl: ${zenDeskTargetUrl}" + System.lineSeparator() +
//			"zenDeskPriority: ${zenDeskPriority}" + System.lineSeparator() +
//			"zenDeskSubject: ${zenDeskSubject}" + System.lineSeparator() +
//			"zenDeskMessageDetails: ${zenDeskMessageDetails}"

		HttpApiClient zenDeskClient = new HttpApiClient()
		Boolean ignoreSsl = false
		AccountCredential accountInfo = context.async.accountCredential.list(
				new DataQuery().withFilter(new DataFilter("id", config.userId)
				)).blockingFirst()
		Map body = [
			request: [
				comment: [
				   body: "zenDeskMessageDetails"
				],
			]
		]
		body.request.priority = zenDeskPriority
		body.request.subject = zenDeskSubject
		body.request.requester = [
			name: "${accountInfo.user.firstName} ${accountInfo.user.lastName}",
			email: "${accountInfo.user.email}"
		]
		body.request.recipient = accountInfo.user.email


		// log.info("creduser: ${userInfo.user.email} ${userInfo.user.firstName} ${userInfo.user.lastName}")
		try {
			ServiceResponse results = zenDeskClient.callJsonApi(zenDeskTargetUrl, apiPath, new HttpApiClient.RequestOptions(contentType:'application/json', ignoreSSL: ignoreSsl, body: body), 'POST')
			String taskResultJson = new JsonBuilder(results['data']).toPrettyString()
			if(results.success) {
				new TaskResult(
					success: true,
					data: taskResultJson,
					output: taskResultJson
				)
			}else{
				new TaskResult(
					success: false,
					data: taskResultJson,
					output: taskResultJson
				)
			}
		} catch(e) {
			e.printStackTrace()
			// log.error("Get Tickets error: ${e}", e)
			new TaskResult(
				success: false,
				data   : e.toString(),
				output : e.toString()
			)
		} finally {
			zenDeskClient.shutdownClient()
		}
	}
}
