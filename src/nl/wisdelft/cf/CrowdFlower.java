package nl.wisdelft.cf;

import nl.wisdelft.cf.datamodel.Job;
import nl.wisdelft.cf.job.JobController;
import nl.wisdelft.cf.unit.UnitController;

import java.util.List;

public interface CrowdFlower {

    /**
     * [ web call ] Gets a list of jobs. If the number of jobs is greater than 10, returns a
     * recent list of 10 jobs
     *
     * @return
     */
    public List<Job> getJobs();

    /**
     * [ non web call ] reads the api key from the defined properties file
     */
    public String getApiKey();

    /**
     * [ non web call ] Returns a new Object of Empty JobController
     *
     * @return
     */
    public JobController getJobController();

    /**
     * [ web call ] Gets account details for the User with the given API key
     *
     * @return
     */

    public Account getAccount();

    public UnitController getUnitController();
}
