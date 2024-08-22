package com.morpheusdata.task


import com.morpheusdata.core.AbstractTaskService
import com.morpheusdata.core.ExecutableTaskInterface
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.providers.TaskProvider
import com.morpheusdata.model.*

/**
 * Example TaskProvider
 */
class ZenDeskTaskProviderCreateRequest implements TaskProvider {
	MorpheusContext morpheusContext
	Plugin plugin
	AbstractTaskService service

    ZenDeskTaskProviderCreateRequest(Plugin plugin, MorpheusContext morpheusContext) {
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
		return new ZenDeskTaskServiceRequest(morpheus)
	}

	@Override
	String getCode() {
		return "zenDeskCreateRequest"
	}

	@Override
	TaskType.TaskScope getScope() {
		return TaskType.TaskScope.all
	}

	@Override
	String getName() {
		return 'Zendesk Create Request'
	}

	@Override
	String getDescription() {
		return 'A custom task that creates a Request in ZenDesk.  Does not require authentication but anonymous requests must be allowed.'
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
		return false
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
			displayOrder: 0,
			fieldLabel: 'Target URL',
			required: true,
			helpText: 'Enter the base URL of the ZenDesk instance, for example:  https://domain.zendesk.com',
			inputType: OptionType.InputType.TEXT)
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskPriority',
			code: 'zenDeskPriority',
			fieldName: 'zenDeskPriority',
			displayOrder: 3,
			fieldLabel: 'Priority',
			required: true,
			optionSource: 'zenDeskPriorityTypeList',
			inputType: OptionType.InputType.SELECT)
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskSubject',
			code: 'zenDeskSubject',
			fieldName: 'zenDeskSubject',
			displayOrder: 4,
			fieldLabel: 'Subject',
			required: true,
			inputType: OptionType.InputType.TEXT)
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskMessageDetails',
			code: 'zenDeskMessageDetails',
			fieldName: 'zenDeskMessageDetails',
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
		return new Icon(path:"zenDeskStandard.png", darkPath: "zenDeskDark.png")
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
