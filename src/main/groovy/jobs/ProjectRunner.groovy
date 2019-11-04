package jobs

/**
 * Created by Kamila Przychodzeń, kamila.przychodzen@gmail.com on 2019-11-03.
 */
class ProjectRunner {
    private List<Project> projects = []

    private Map baseTypes = [
            'ParallelExecutionProject'  : { name -> new ParallelExecutionProject(name) },
            'SequentialExecutionProject': { name -> new SequentialExecutionProject(name) }
    ]

    void generateJobs(parentJob, projects) {
        println('Inside generateJobs method')
        projects.each({ project ->
            println("Project processing " + project.toString())
            printf 'processing ' + project.name + ' of type ' + project.base

            if (project instanceof ParallelExecutionProject) {
                println('Generate pipeline for ParallelExecutionProject')
                parentJob.pipelineJob(project.name) {
                    parameters {
                        stringParam("jobName", project.name)
                    }
                    definition {
                        cps {
                            script(
                                    readFileFromWorkspace('JenkinsParallelPipeline.groovy')
                            )
                        }
                    }
                }
            } else if (project instanceof SequentialExecutionProject) {
                parentJob.pipelineJob("${project.name}") {
                    definition {
                        cps {
                            script(
                                    readFileFromWorkspace('JenkinsSequentialPipeline.groovy')
                            )
                        }
                    }
                }
            }

        })
    }

    def project(Map<String, Object> inputs, Closure configurationClosure = null) {
        def name = inputs['name']
        def base = inputs['base']
        // ?: Is the specified base a valid basetype?
        if (!baseTypes.containsKey(base)) {
            // -> No, throw exception!
            throw new IllegalArgumentException("ProjectDSLTrait [$name] did not have a valid base. Choose among: ${baseTypes.keySet()}")
        }
        // E -> apply the base configuration
        Project project = baseTypes[base](name)

        // Run the configuration closure if it was provided
        if (configurationClosure != null) {
            project.with configurationClosure
        }

        projects.add(project)
    }
}
