import jobs.ProjectRunner

def projects = {
    new ProjectRunner().project(name: 'ProjectParallel', base: 'ParallelExecutionProject') {}
    new ProjectRunner().project(name: 'ProjectSequential', base: 'SequentialExecutionProject'){}
}


println('Start processing')
new ProjectRunner().generateJobs(this, projects);


return this
