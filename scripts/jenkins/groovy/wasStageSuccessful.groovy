def call(String stageName) {
  def STAGE_END_TYPE_DISPLAY_NAME = 'Stage : Body : End'

  def prevBuildRaw = currentBuild.prevBuild.getRawBuild()

  echo "###### Checking if ${stageName} was successful in ${env.JOB_NAME} #${prevBuildRaw} ######"

  for (node in prevBuildRaw.getAction(org.jenkinsci.plugins.workflow.job.views.FlowGraphAction.class).getNodes()) {
    if (node.getError() != null && node.getTypeDisplayName() == STAGE_END_TYPE_DISPLAY_NAME) {
      if (node.getStartNode().getDisplayName() == stageName) {
        echo "###### ${stageName} was FAILED in ${prevBuildRaw} ######"
        return false
      }
    }
  }
  echo "###### ${stageName} was successful or not present in ${env.JOB_NAME} #${prevBuildRaw} ######"
  return true
}

return true
