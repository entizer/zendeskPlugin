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
class ZenDeskTaskProviderCreateObject implements TaskProvider {
	MorpheusContext morpheusContext
	Plugin plugin
	AbstractTaskService service

	ZenDeskTaskProviderCreateObject(Plugin plugin, MorpheusContext morpheusContext) {
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
		return "zenDeskCreateObject"
	}

	@Override
	TaskType.TaskScope getScope() {
		return TaskType.TaskScope.all
	}

	@Override
	String getName() {
		return 'Zendesk Create Object'
	}

	@Override
	String getDescription() {
		return 'A custom task that creates an object in ZenDesk'
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
		OptionType optionType = new OptionType(
				name: 'zenDeskTargetUrl2',
				code: 'zenDeskTargetUrl2',
				fieldName: 'zenDeskTargetUrl2',
				optionSource: true,
				displayOrder: 0,
				fieldLabel: 'Target URL2',
				required: true,
				inputType: OptionType.InputType.TEXT
		)
		return [optionType]
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
