package com.morpheusdata.task

import com.morpheusdata.core.*
import com.morpheusdata.core.providers.TaskProvider
import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.Icon
import com.morpheusdata.model.Instance
import com.morpheusdata.model.OptionType
import com.morpheusdata.model.Task
import com.morpheusdata.model.TaskResult
import com.morpheusdata.model.TaskType
import com.morpheusdata.model.Workload

/**
 * Example TaskProvider
 */
class ZenDeskTaskProviderCreateTicket implements TaskProvider {
	MorpheusContext morpheusContext
	Plugin plugin
	AbstractTaskService service

	ZenDeskTaskProviderCreateTicket(Plugin plugin, MorpheusContext morpheusContext) {
		this.plugin = plugin
		this.morpheusContext = morpheusContext
	}

	@Override
	MorpheusContext getMorpheus() {
		return morpheusContext
	}

	@Override
	Plugin getPlugin() {
		return plugin
	}

	@Override
	ExecutableTaskInterface getService() {
		return new ZenDeskTaskService(morpheus)
	}

	@Override
	String getCode() {
		return "zenDeskCreateTicket"
	}

	@Override
	TaskType.TaskScope getScope() {
		return TaskType.TaskScope.all
	}

	@Override
	String getName() {
		return 'Zendesk Create Ticket'
	}

	@Override
	String getDescription() {
		return 'A custom task that creates a Ticket in ZenDesk.  Requires authentication and a service account.'
	}

	@Override
	Boolean isAllowExecuteLocal() {
		return true
	}

	@Override
	Boolean isAllowExecuteRemote() {
		return false
	}

	@Override
	Boolean isAllowExecuteResource() {
		return false
	}

	@Override
	Boolean isAllowLocalRepo() {
		return true
	}

	@Override
	Boolean isAllowRemoteKeyAuth() {
		return true
	}

	@Override
	Boolean hasResults() {
		return true
	}

	/**
	 * Builds an OptionType to take some text
	 * @return list of OptionType
	 */
	@Override
	List<OptionType> getOptionTypes() {
		List<OptionType> optionTypes = new ArrayList<OptionType>()
		optionTypes.add(new OptionType(
			name: 'zenDeskTargetUrl',
			code: 'zenDeskTargetUrl',
			fieldName: 'zenDeskTargetUrl',
			optionSource: true,
			displayOrder: 0,
			fieldLabel: 'Target URL',
			required: true,
			inputType: OptionType.InputType.TEXT)
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskAuthUsername',
			code: 'zenDeskAuthUsername',
			fieldName: 'zenDeskAuthUsername',
			optionSource: true,
			displayOrder: 1,
			fieldLabel: 'Auth Username',
			required: true,
			inputType: OptionType.InputType.TEXT)
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskAuthApiToken',
			code: 'zenDeskAuthApiToken',
			fieldName: 'zenDeskAuthApiToken',
			optionSource: true,
			displayOrder: 2,
			fieldLabel: 'Auth API Token',
			required: true,
			inputType: OptionType.InputType.PASSWORD)
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskPriority',
			code: 'zenDeskPriority',
			fieldName: 'zenDeskPriority',
			optionSource: true,
			displayOrder: 3,
			fieldLabel: 'Priority',
			required: true,
			inputType: OptionType.InputType.TEXT)
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskSubject',
			code: 'zenDeskSubject',
			fieldName: 'zenDeskSubject',
			optionSource: true,
			displayOrder: 4,
			fieldLabel: 'Subject',
			required: true,
			inputType: OptionType.InputType.TEXT)
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskMessageDetails',
			code: 'zenDeskMessageDetails',
			fieldName: 'zenDeskMessageDetails',
			optionSource: true,
			displayOrder: 5,
			fieldLabel: 'Message Details',
			required: true,
			inputType: OptionType.InputType.TEXTAREA)
		)
		return optionTypes
	}

	/**
	 * Returns the Task Type Icon for display when a user is browsing tasks
	 * @since 0.12.7
	 * @return Icon representation of assets stored in the src/assets/images of the project.
	 */
	@Override
	Icon getIcon() {
		return new Icon(path:"zenDesk.png", darkPath: "zenDesk.png")
	}

	@Override
	TaskResult executeLocalTask(Task task, Map map, Workload workload, ComputeServer computeServer, Instance instance) {
		return null
	}

	@Override
	TaskResult executeServerTask(ComputeServer computeServer, Task task, Map map) {
		return null
	}

	@Override
	TaskResult executeServerTask(ComputeServer computeServer, Task task) {
		return null
	}

	@Override
	TaskResult executeContainerTask(Workload workload, Task task, Map map) {
		return null
	}

	@Override
	TaskResult executeContainerTask(Workload workload, Task task) {
		return null
	}

	@Override
	TaskResult executeRemoteTask(Task task, Map map, Workload workload, ComputeServer computeServer, Instance instance) {
		return null
	}

	@Override
	TaskResult executeRemoteTask(Task task, Workload workload, ComputeServer computeServer, Instance instance) {
		return null
	}
}
