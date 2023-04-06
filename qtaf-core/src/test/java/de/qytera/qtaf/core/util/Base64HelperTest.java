package de.qytera.qtaf.core.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class Base64HelperTest {
    @Test
    public void testBase64Encoding() {
        String input1 = "Hello World";
        String output1 = Base64Helper.encode(input1);
        String expectedOutput1 = "SGVsbG8gV29ybGQ=";

        Assert.assertEquals(output1, expectedOutput1);
    }

    @Test
    public void testBase64ImageFileEncoding() throws IOException {
        String filepath = "$USER_DIR/src/test/resources/de/qytera/qtaf/core/util/passed.png";
        String output1 = Base64Helper.encodeFileContent(filepath);
        String expectedOutput1 = "iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAADHmlDQ1BJQ0MgUHJvZmlsZQAAeAGFVN9r01AU/tplnbDhizpnEQk+aJFuZFN0Q5y2a1e6zVrqNrchSJumbVyaxiTtfrAH2YtvOsV38Qc++QcM2YNve5INxhRh+KyIIkz2IrOemzRNJ1MDufe73/nuOSfn5F6g+XFa0xQvDxRVU0/FwvzE5BTf8gFeHEMr/GhNi4YWSiZHQA/Tsnnvs/MOHsZsdO5v36v+Y9WalQwR8BwgvpQ1xCLhWaBpXNR0E+DWie+dMTXCzUxzWKcECR9nOG9jgeGMjSOWZjQ1QJoJwgfFQjpLuEA4mGng8w3YzoEU5CcmqZIuizyrRVIv5WRFsgz28B9zg/JfsKiU6Zut5xCNbZoZTtF8it4fOX1wjOYA1cE/Xxi9QbidcFg246M1fkLNJK4RJr3n7nRpmO1lmpdZKRIlHCS8YlSuM2xp5gsDiZrm0+30UJKwnzS/NDNZ8+PtUJUE6zHF9fZLRvS6vdfbkZMH4zU+pynWf0D+vff1corleZLw67QejdX0W5I6Vtvb5M2mI8PEd1E/A0hCgo4cZCjgkUIMYZpjxKr4TBYZIkqk0ml0VHmyONY7KJOW7RxHeMlfDrheFvVbsrj24Pue3SXXjrwVhcW3o9hR7bWB6bqyE5obf3VhpaNu4Te55ZsbbasLCFH+iuWxSF5lyk+CUdd1NuaQU5f8dQvPMpTuJXYSWAy6rPBe+CpsCk+FF8KXv9TIzt6tEcuAcSw+q55TzcbsJdJM0utkuL+K9ULGGPmQMUNanb4kTZyKOfLaUAsnBneC6+biXC/XB567zF3h+rkIrS5yI47CF/VFfCHwvjO+Pl+3b4hhp9u+02TrozFa67vTkbqisXqUj9sn9j2OqhMZsrG+sX5WCCu0omNqSrN0TwADJW1Ol/MFk+8RhAt8iK4tiY+rYleQTysKb5kMXpcMSa9I2S6wO4/tA7ZT1l3maV9zOfMqcOkb/cPrLjdVBl4ZwNFzLhegM3XkCbB8XizrFdsfPJ63gJE722OtPW1huos+VqvbdC5bHgG7D6vVn8+q1d3n5H8LeKP8BqkjCtbCoV8yAAAAmElEQVQoFWOUnKj7n4EEwESCWrBSkjWwoNtgK+OGIpSsFcTgpmHFIDVJj7ANIUquYMX5a1vghqA46VneJYYf37/DcaiBO0PvvvkMq5+ugmtAcRLIpI3RUxlWX9jJ8PH7Z7CiQ8/PwBWDGCgawCatZWCYGFwDVlS7dRLDl+/fUDSgOAkkc+X9XQaQQhA49vw8mEYmGAdfxAEAnGs0JZMXvagAAAAASUVORK5CYII=";

        Assert.assertEquals(output1, expectedOutput1);
    }

    @Test
    public void testBase64TextFileEncoding() throws IOException {
        String filepath = "$USER_DIR/src/test/resources/de/qytera/qtaf/core/util/loremipsum.txt";
        String output1 = Base64Helper.encodeFileContent(filepath);
        String expectedOutput1 = "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNldGV0dXIgc2FkaXBzY2luZyBlbGl0ciwgc2VkIGRpYW0gbm9udW15IGVpcm1vZCB0ZW1wb3IgaW52aWR1bnQgdXQgbGFib3JlIGV0IGRvbG9yZSBtYWduYSBhbGlxdXlhbSBlcmF0LCBzZWQgZGlhbSB2b2x1cHR1YS4=";

        Assert.assertEquals(output1, expectedOutput1);
    }

    @Test
    public void testBase64FileDecoding() throws IOException {
        String filepath = "$USER_DIR/src/test/resources/de/qytera/qtaf/core/util/loremipsum.base64.txt";
        String output1 = Base64Helper.decodeFileContentAsString(filepath);
        String expectedOutput1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

        Assert.assertEquals(output1, expectedOutput1);
    }

    @Test
    public void testBase64Decoding() {
        String input1 = "SGVsbG8gV29ybGQ=";
        String output1 = Base64Helper.decode(input1);
        String expectedOutput1 = "Hello World";

        Assert.assertEquals(output1, expectedOutput1);
    }
}
