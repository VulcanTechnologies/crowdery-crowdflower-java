package nl.wisdelft.cf.job;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * This is class is analogous to Channels in CrowdFlower
 * You can set or get enabled and available channels
 *
 */
public class Channel {

    private Set<String> availableChannels;
    private Set<String> enabledChannels;
    private String theJobID;

    public Channel(String jobID)
    {
        this(jobID, new HashSet<>(), new HashSet<>());
    }

    Channel(String aJobId, Set<String> aAvailableChannels, Set<String> aEnabledChannels)
    {
        theJobID = aJobId;
        availableChannels = aAvailableChannels;
        enabledChannels = aEnabledChannels;
    }

    /**
     * Returns a List of enabled channels
     * @return List
     */
    public List<String> getEnabledChannels()
    {
        return Lists.newArrayList(enabledChannels);
    }

    /**
     * Returns a List of available channels
     * @return
     */

    public List<String> getAvailableChannels()
    {
        return Lists.newArrayList(availableChannels);
    }

    public void setChannels(
            List<String> available,
            List<String> enabled)
    {
        availableChannels = Sets.newHashSet(available);
        enabledChannels = Sets.newHashSet(enabled);
    }

    public String getJobID()
    {
        return theJobID;
    }
}
