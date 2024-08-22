package com.morpheusdata.task

import com.morpheusdata.core.AbstractTaskService
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.util.HttpApiClient
import com.morpheusdata.model.*
import com.morpheusdata.response.ServiceResponse
import com.morpheusdata.views.ViewModel
import groovy.json.JsonBuilder

/**
 * Example AbstractTaskService. Each method demonstrates building an example TaskConfig for the relevant task type
 */
class ZenDeskTaskServiceGroup extends AbstractTaskService {
	MorpheusContext context

    ZenDeskTaskServiceGroup(MorpheusContext context) {
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
		String apiPath = '/api/v2/groups'
		// username@domain.com/token
		String zenDeskAuthUsername = task.taskOptions.find { it.optionType.code == 'zenDeskAuthUsername'}?.value + '/token'
		String zenDeskAuthApiToken = task.taskOptions.find { it.optionType.code == 'zenDeskAuthApiToken'}?.value
		String zenDeskGroupName = task.taskOptions.find { it.optionType.code == 'zenDeskGroupName'}?.value
		User userinfo = new ViewModel().user

//		String data = "zenDeskTargetUrl: ${zenDeskTargetUrl}" + System.lineSeparator() +
//			"zenDeskAuthUsername: ${zenDeskAuthUsername + '/token'}" + System.lineSeparator() +
//			"zenDeskAuthApiToken: ${zenDeskAuthApiToken}" + System.lineSeparator() +
//			"zenDeskPriority: ${zenDeskGroupName}" + System.lineSeparator()

		HttpApiClient zenDeskClient = new HttpApiClient()
		Boolean ignoreSsl = false
		Map body = [
			group: [
				name: zenDeskGroupName
			]
		]

		try {
			ServiceResponse results = zenDeskClient.callJsonApi(zenDeskTargetUrl, apiPath, zenDeskAuthUsername, zenDeskAuthApiToken, new HttpApiClient.RequestOptions(contentType:'application/json', ignoreSSL: ignoreSsl, body: body), 'POST')
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
