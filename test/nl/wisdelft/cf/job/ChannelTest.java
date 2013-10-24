package nl.wisdelft.cf.job;

import com.google.common.collect.*;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: Debarshi Basak
 */
public class ChannelTest {

    private Channel theChannel;

    @Before
    public void setUp()
    {
        Set<String> myAvailableChannels = Sets.newHashSet("test-available");
        Set<String> myEnabledChannels = Sets.newHashSet("test-enabled");

        theChannel = new Channel("test-job-id",
                                 myAvailableChannels,
                                 myEnabledChannels);
    }

    @Test
    public void shouldReturnAvailableChannels()
    {
        assertThat(
                Iterables
                        .getOnlyElement(
                                theChannel.getAvailableChannels()
                        )
        ).isEqualTo("test-available");
    }

    @Test
    public void shouldReturn()
    {
        assertThat(
                Iterables
                        .getOnlyElement(
                                theChannel.getEnabledChannels()
                        )
        ).isEqualTo("test-enabled");
    }

}
