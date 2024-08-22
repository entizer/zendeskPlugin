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
class ZenDeskTaskProviderCreateGroup implements TaskProvider {
	MorpheusContext morpheusContext
	Plugin plugin
	AbstractTaskService service

    ZenDeskTaskProviderCreateGroup(Plugin plugin, MorpheusContext morpheusContext) {
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
		return new ZenDeskTaskServiceGroup(morpheus)
	}

	@Override
	String getCode() {
		return "zenDeskCreateGroup"
	}

	@Override
	TaskType.TaskScope getScope() {
		return TaskType.TaskScope.all
	}

	@Override
	String getName() {
		return 'Zendesk Create Group'
	}

	@Override
	String getDescription() {
		return 'A custom task that creates a Group in ZenDesk.  Requires authentication and a service account.'
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
			name: 'zenDeskAuthUsername',
			code: 'zenDeskAuthUsername',
			fieldName: 'zenDeskAuthUsername',
			displayOrder: 1,
			fieldLabel: 'Auth Username',
			required: true,
			inputType: OptionType.InputType.TEXT,
			placeHolderText: "username@domain.com",
			helpText: "Should NOT include /token")
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskAuthApiToken',
			code: 'zenDeskAuthApiToken',
			fieldName: 'zenDeskAuthApiToken',
			displayOrder: 2,
			fieldLabel: 'Auth API Token',
			required: true,
			inputType: OptionType.InputType.PASSWORD,
			helpText: "Generated for a user")
		)
		optionTypes.add(new OptionType(
			name: 'zenDeskGroupName',
			code: 'zenDeskGroupName',
			fieldName: 'zenDeskGroupName',
			displayOrder: 3,
			fieldLabel: 'Group Name',
			required: true,
			inputType: OptionType.InputType.TEXT)
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
