package nl.wisdelft.cf;

import org.jetbrains.annotations.*;
import org.junit.*;
import org.mockito.*;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CrowdFlowerImplTest {

    @NotNull
    @Mock
    private Properties theProperties;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnApiKey()
    {
        when(theProperties.getProperty("apiKey")).thenReturn("test-api-key");
        CrowdFlower myCrowdFlower = new CrowdFlowerImpl(theProperties);
        assertThat(myCrowdFlower.getApiKey()).isEqualTo("test-api-key");
    }
}
