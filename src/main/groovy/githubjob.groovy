import jobs.ProjectRunner

def projects = {
    project(name: 'ProjectParallel', base: 'ParallelExecutionProject') {}
    project(name: 'ProjectSequential', base: 'SequentialExecutionProject'){}
}


println('Start processing')
new ProjectRunner().generateJobs(this, projects);

return this
