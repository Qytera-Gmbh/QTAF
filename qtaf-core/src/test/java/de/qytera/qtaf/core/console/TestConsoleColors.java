package de.qytera.qtaf.core.console;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.console.ConsoleColors;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestConsoleColors {
    private static String COLOR_CONFIG_KEY = "console.colors";
    @Test(testName = "black with enabled")
    public void testBlackWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLACK_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blackBold(testString), expected);
    }

    @Test(testName = "black with disabled")
    public void testBlackWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blackBold(testString), testString);
    }

    // Test cases for RED
    @Test(testName = "red with enabled")
    public void testRedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.RED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.red(testString), expected);
    }

    @Test(testName = "red with disabled")
    public void testRedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.red(testString), testString);
    }

    // Test cases for GREEN
    @Test(testName = "green with enabled")
    public void testGreenWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.GREEN + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.green(testString), expected);
    }

    @Test(testName = "green with disabled")
    public void testGreenWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.green(testString), testString);
    }

    // Test cases for YELLOW
    @Test(testName = "yellow with enabled")
    public void testYellowWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.YELLOW + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.yellow(testString), expected);
    }

    @Test(testName = "yellow with disabled")
    public void testYellowWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.yellow(testString), testString);
    }

    // Test cases for BLUE
    @Test(testName = "blue with enabled")
    public void testBlueWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLUE + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blue(testString), expected);
    }

    @Test(testName = "blue with disabled")
    public void testBlueWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blue(testString), testString);
    }

    // Test cases for PURPLE
    @Test(testName = "purple with enabled")
    public void testPurpleWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.PURPLE + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.purple(testString), expected);
    }

    @Test(testName = "purple with disabled")
    public void testPurpleWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.purple(testString), testString);
    }

    // Test cases for CYAN
    @Test(testName = "cyan with enabled")
    public void testCyanWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.CYAN + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.cyan(testString), expected);
    }

    @Test(testName = "cyan with disabled")
    public void testCyanWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.cyan(testString), testString);
    }

    // Test cases for WHITE
    @Test(testName = "white with enabled")
    public void testWhiteWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.WHITE + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.white(testString), expected);
    }

    @Test(testName = "white with disabled")
    public void testWhiteWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.white(testString), testString);
    }

    // Test cases for BLACK_BOLD
    @Test(testName = "blackBold with enabled")
    public void testBlackBoldWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLACK_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blackBold(testString), expected);
    }

    @Test(testName = "blackBold with disabled")
    public void testBlackBoldWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blackBold(testString), testString);
    }

    // Test cases for RED_BOLD
    @Test(testName = "redBold with enabled")
    public void testRedBoldWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.RED_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.redBold(testString), expected);
    }

    @Test(testName = "redBold with disabled")
    public void testRedBoldWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.redBold(testString), testString);
    }

    // Test cases for GREEN_BOLD
    @Test(testName = "greenBold with enabled")
    public void testGreenBoldWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.GREEN_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.greenBold(testString), expected);
    }

    @Test(testName = "greenBold with disabled")
    public void testGreenBoldWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.greenBold(testString), testString);
    }

    // Test cases for YELLOW_BOLD
    @Test(testName = "yellowBold with enabled")
    public void testYellowBoldWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.YELLOW_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.yellowBold(testString), expected);
    }

    @Test(testName = "yellowBold with disabled")
    public void testYellowBoldWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.yellowBold(testString), testString);
    }

    // Test cases for BLUE_BOLD
    @Test(testName = "blueBold with enabled")
    public void testBlueBoldWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLUE_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blueBold(testString), expected);
    }

    @Test(testName = "blueBold with disabled")
    public void testBlueBoldWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blueBold(testString), testString);
    }

    // Test cases for PURPLE_BOLD
    @Test(testName = "purpleBold with enabled")
    public void testPurpleBoldWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.PURPLE_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.purpleBold(testString), expected);
    }

    @Test(testName = "purpleBold with disabled")
    public void testPurpleBoldWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.purpleBold(testString), testString);
    }

    // Test cases for CYAN_BOLD
    @Test(testName = "cyanBold with enabled")
    public void testCyanBoldWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.CYAN_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.cyanBold(testString), expected);
    }

    @Test(testName = "cyanBold with disabled")
    public void testCyanBoldWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.cyanBold(testString), testString);
    }

    // Test cases for WHITE_BOLD
    @Test(testName = "whiteBold with enabled")
    public void testWhiteBoldWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.WHITE_BOLD + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.whiteBold(testString), expected);
    }

    @Test(testName = "whiteBold with disabled")
    public void testWhiteBoldWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.whiteBold(testString), testString);
    }

    // Test cases for BLACK_UNDERLINED
    @Test(testName = "blackUnderlined with enabled")
    public void testBlackUnderlinedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLACK_UNDERLINED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blackUnderlined(testString), expected);
    }

    @Test(testName = "blackUnderlined with disabled")
    public void testBlackUnderlinedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blackUnderlined(testString), testString);
    }

    // Test cases for RED_UNDERLINED
    @Test(testName = "redUnderlined with enabled")
    public void testRedUnderlinedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.RED_UNDERLINED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.redUnderlined(testString), expected);
    }

    @Test(testName = "redUnderlined with disabled")
    public void testRedUnderlinedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.redUnderlined(testString), testString);
    }

    // Test cases for GREEN_UNDERLINED
    @Test(testName = "greenUnderlined with enabled")
    public void testGreenUnderlinedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.GREEN_UNDERLINED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.greenUnderlined(testString), expected);
    }

    @Test(testName = "greenUnderlined with disabled")
    public void testGreenUnderlinedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.greenUnderlined(testString), testString);
    }

    // Test cases for YELLOW_UNDERLINED
    @Test(testName = "yellowUnderlined with enabled")
    public void testYellowUnderlinedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.YELLOW_UNDERLINED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.yellowUnderlined(testString), expected);
    }

    @Test(testName = "yellowUnderlined with disabled")
    public void testYellowUnderlinedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.yellowUnderlined(testString), testString);
    }

    // Test cases for BLUE_UNDERLINED
    @Test(testName = "blueUnderlined with enabled")
    public void testBlueUnderlinedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLUE_UNDERLINED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blueUnderlined(testString), expected);
    }

    @Test(testName = "blueUnderlined with disabled")
    public void testBlueUnderlinedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blueUnderlined(testString), testString);
    }

    // Test cases for PURPLE_UNDERLINED
    @Test(testName = "purpleUnderlined with enabled")
    public void testPurpleUnderlinedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.PURPLE_UNDERLINED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.purpleUnderlined(testString), expected);
    }

    @Test(testName = "purpleUnderlined with disabled")
    public void testPurpleUnderlinedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.purpleUnderlined(testString), testString);
    }

    // Test cases for CYAN_UNDERLINED
    @Test(testName = "cyanUnderlined with enabled")
    public void testCyanUnderlinedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.CYAN_UNDERLINED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.cyanUnderlined(testString), expected);
    }

    @Test(testName = "cyanUnderlined with disabled")
    public void testCyanUnderlinedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.cyanUnderlined(testString), testString);
    }

    // Test cases for WHITE_UNDERLINED
    @Test(testName = "whiteUnderlined with enabled")
    public void testWhiteUnderlinedWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.WHITE_UNDERLINED + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.whiteUnderlined(testString), expected);
    }

    @Test(testName = "whiteUnderlined with disabled")
    public void testWhiteUnderlinedWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.whiteUnderlined(testString), testString);
    }

    // Test cases for BLACK_BACKGROUND
    @Test(testName = "blackBg with enabled")
    public void testBlackBgWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLACK_BACKGROUND + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blackBg(testString), expected);
    }

    @Test(testName = "blackBg with disabled")
    public void testBlackBgWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blackBg(testString), testString);
    }

    // Test cases for RED_BACKGROUND
    @Test(testName = "redBg with enabled")
    public void testRedBgWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.RED_BACKGROUND + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.redBg(testString), expected);
    }

    @Test(testName = "redBg with disabled")
    public void testRedBgWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.redBg(testString), testString);
    }

    // Test cases for GREEN_BACKGROUND
    @Test(testName = "greenBg with enabled")
    public void testGreenBgWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.GREEN_BACKGROUND + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.greenBg(testString), expected);
    }

    @Test(testName = "greenBg with disabled")
    public void testGreenBgWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.greenBg(testString), testString);
    }

    // Test cases for YELLOW_BACKGROUND
    @Test(testName = "yellowBg with enabled")
    public void testYellowBgWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.YELLOW_BACKGROUND + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.yellowBg(testString), expected);
    }

    @Test(testName = "yellowBg with disabled")
    public void testYellowBgWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.yellowBg(testString), testString);
    }

    // Test cases for BLUE_BACKGROUND
    @Test(testName = "blueBg with enabled")
    public void testBlueBgWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLUE_BACKGROUND + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blueBg(testString), expected);
    }

    @Test(testName = "blueBg with disabled")
    public void testBlueBgWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blueBg(testString), testString);
    }

    // Test cases for PURPLE_BACKGROUND
    @Test(testName = "purpleBg with enabled")
    public void testPurpleBgWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.PURPLE_BACKGROUND + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.purpleBg(testString), expected);
    }

    @Test(testName = "purpleBg with disabled")
    public void testPurpleBgWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.purpleBg(testString), testString);
    }

    // Test cases for CYAN_BACKGROUND
    @Test(testName = "cyanBg with enabled")
    public void testCyanBgWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.CYAN_BACKGROUND + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.cyanBg(testString), expected);
    }

    @Test(testName = "cyanBg with disabled")
    public void testCyanBgWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.cyanBg(testString), testString);
    }

    // Test cases for WHITE_BACKGROUND
    @Test(testName = "whiteBg with enabled")
    public void testWhiteBgWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.WHITE_BACKGROUND + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.whiteBg(testString), expected);
    }

    @Test(testName = "whiteBg with disabled")
    public void testWhiteBgWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.whiteBg(testString), testString);
    }

    // Test cases for BLACK_BRIGHT
    @Test(testName = "blackBright with enabled")
    public void testBlackBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLACK_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blackBright(testString), expected);
    }

    @Test(testName = "blackBright with disabled")
    public void testBlackBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blackBright(testString), testString);
    }

    // Test cases for RED_BRIGHT
    @Test(testName = "redBright with enabled")
    public void testRedBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.RED_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.redBright(testString), expected);
    }

    @Test(testName = "redBright with disabled")
    public void testRedBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.redBright(testString), testString);
    }

    // Test cases for GREEN_BRIGHT
    @Test(testName = "greenBright with enabled")
    public void testGreenBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.GREEN_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.greenBright(testString), expected);
    }

    @Test(testName = "greenBright with disabled")
    public void testGreenBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.greenBright(testString), testString);
    }

    // Test cases for YELLOW_BRIGHT
    @Test(testName = "yellowBright with enabled")
    public void testYellowBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.YELLOW_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.yellowBright(testString), expected);
    }

    @Test(testName = "yellowBright with disabled")
    public void testYellowBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.yellowBright(testString), testString);
    }

    // Test cases for BLUE_BRIGHT
    @Test(testName = "blueBright with enabled")
    public void testBlueBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLUE_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blueBright(testString), expected);
    }

    @Test(testName = "blueBright with disabled")
    public void testBlueBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blueBright(testString), testString);
    }

    // Test cases for PURPLE_BRIGHT
    @Test(testName = "purpleBright with enabled")
    public void testPurpleBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.PURPLE_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.purpleBright(testString), expected);
    }

    @Test(testName = "purpleBright with disabled")
    public void testPurpleBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.purpleBright(testString), testString);
    }

    // Test cases for CYAN_BRIGHT
    @Test(testName = "cyanBright with enabled")
    public void testCyanBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.CYAN_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.cyanBright(testString), expected);
    }

    @Test(testName = "cyanBright with disabled")
    public void testCyanBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.cyanBright(testString), testString);
    }

    // Test cases for WHITE_BRIGHT
    @Test(testName = "whiteBright with enabled")
    public void testWhiteBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.WHITE_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.whiteBright(testString), expected);
    }

    @Test(testName = "whiteBright with disabled")
    public void testWhiteBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.whiteBright(testString), testString);
    }

    // Test cases for BLACK_BOLD_BRIGHT
    @Test(testName = "blackBoldBright with enabled")
    public void testBlackBoldBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLACK_BOLD_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blackBoldBright(testString), expected);
    }

    @Test(testName = "blackBoldBright with disabled")
    public void testBlackBoldBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blackBoldBright(testString), testString);
    }

    // Test cases for RED_BOLD_BRIGHT
    @Test(testName = "redBoldBright with enabled")
    public void testRedBoldBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.RED_BOLD_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.redBoldBright(testString), expected);
    }

    @Test(testName = "redBoldBright with disabled")
    public void testRedBoldBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.redBoldBright(testString), testString);
    }

    // Test cases for GREEN_BOLD_BRIGHT
    @Test(testName = "greenBoldBright with enabled")
    public void testGreenBoldBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.GREEN_BOLD_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.greenBoldBright(testString), expected);
    }

    @Test(testName = "greenBoldBright with disabled")
    public void testGreenBoldBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.greenBoldBright(testString), testString);
    }

    // Test cases for YELLOW_BOLD_BRIGHT
    @Test(testName = "yellowBoldBright with enabled")
    public void testYellowBoldBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.YELLOW_BOLD_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.yellowBoldBright(testString), expected);
    }

    @Test(testName = "yellowBoldBright with disabled")
    public void testYellowBoldBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.yellowBoldBright(testString), testString);
    }

    // Test cases for BLUE_BOLD_BRIGHT
    @Test(testName = "blueBoldBright with enabled")
    public void testBlueBoldBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLUE_BOLD_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blueBoldBright(testString), expected);
    }

    @Test(testName = "blueBoldBright with disabled")
    public void testBlueBoldBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blueBoldBright(testString), testString);
    }

    // Test cases for PURPLE_BOLD_BRIGHT
    @Test(testName = "purpleBoldBright with enabled")
    public void testPurpleBoldBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.PURPLE_BOLD_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.purpleBoldBright(testString), expected);
    }

    @Test(testName = "purpleBoldBright with disabled")
    public void testPurpleBoldBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.purpleBoldBright(testString), testString);
    }

    // Test cases for CYAN_BOLD_BRIGHT
    @Test(testName = "cyanBoldBright with enabled")
    public void testCyanBoldBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.CYAN_BOLD_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.cyanBoldBright(testString), expected);
    }

    @Test(testName = "cyanBoldBright with disabled")
    public void testCyanBoldBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.cyanBoldBright(testString), testString);
    }

    // Test cases for WHITE_BOLD_BRIGHT
    @Test(testName = "whiteBoldBright with enabled")
    public void testWhiteBoldBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.WHITE_BOLD_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.whiteBoldBright(testString), expected);
    }

    @Test(testName = "whiteBoldBright with disabled")
    public void testWhiteBoldBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.whiteBoldBright(testString), testString);
    }

    // Test cases for BLACK_BACKGROUND_BRIGHT
    @Test(testName = "blackBackgroundBright with enabled")
    public void testBlackBackgroundBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLACK_BACKGROUND_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blackBackgroundBright(testString), expected);
    }

    @Test(testName = "blackBackgroundBright with disabled")
    public void testBlackBackgroundBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blackBackgroundBright(testString), testString);
    }

    // Test cases for RED_BACKGROUND_BRIGHT
    @Test(testName = "redBackgroundBright with enabled")
    public void testRedBackgroundBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.RED_BACKGROUND_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.redBackgroundBright(testString), expected);
    }

    @Test(testName = "redBackgroundBright with disabled")
    public void testRedBackgroundBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.redBackgroundBright(testString), testString);
    }

    // Test cases for GREEN_BACKGROUND_BRIGHT
    @Test(testName = "greenBackgroundBright with enabled")
    public void testGreenBackgroundBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.GREEN_BACKGROUND_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.greenBackgroundBright(testString), expected);
    }

    @Test(testName = "greenBackgroundBright with disabled")
    public void testGreenBackgroundBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.greenBackgroundBright(testString), testString);
    }

    // Test cases for YELLOW_BACKGROUND_BRIGHT
    @Test(testName = "yellowBackgroundBright with enabled")
    public void testYellowBackgroundBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.YELLOW_BACKGROUND_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.yellowBackgroundBright(testString), expected);
    }

    @Test(testName = "yellowBackgroundBright with disabled")
    public void testYellowBackgroundBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.yellowBackgroundBright(testString), testString);
    }

    // Test cases for BLUE_BACKGROUND_BRIGHT
    @Test(testName = "blueBackgroundBright with enabled")
    public void testBlueBackgroundBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.BLUE_BACKGROUND_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.blueBackgroundBright(testString), expected);
    }

    @Test(testName = "blueBackgroundBright with disabled")
    public void testBlueBackgroundBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.blueBackgroundBright(testString), testString);
    }

    // Test cases for PURPLE_BACKGROUND_BRIGHT
    @Test(testName = "purpleBackgroundBright with enabled")
    public void testPurpleBackgroundBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.PURPLE_BACKGROUND_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.purpleBackgroundBright(testString), expected);
    }

    @Test(testName = "purpleBackgroundBright with disabled")
    public void testPurpleBackgroundBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.purpleBackgroundBright(testString), testString);
    }

    // Test cases for CYAN_BACKGROUND_BRIGHT
    @Test(testName = "cyanBackgroundBright with enabled")
    public void testCyanBackgroundBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.CYAN_BACKGROUND_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.cyanBackgroundBright(testString), expected);
    }

    @Test(testName = "cyanBackgroundBright with disabled")
    public void testCyanBackgroundBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.cyanBackgroundBright(testString), testString);
    }

    // Test cases for WHITE_BACKGROUND_BRIGHT
    @Test(testName = "whiteBackgroundBright with enabled")
    public void testWhiteBackgroundBrightWithEnabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, true);
        String testString = "test";
        String expected = ConsoleColors.WHITE_BACKGROUND_BRIGHT + testString + ConsoleColors.RESET;
        Assert.assertEquals(ConsoleColors.whiteBackgroundBright(testString), expected);
    }

    @Test(testName = "whiteBackgroundBright with disabled")
    public void testWhiteBackgroundBrightWithDisabled() {
        QtafFactory.getConfiguration().setBoolean(COLOR_CONFIG_KEY, false);
        String testString = "test";
        Assert.assertEquals(ConsoleColors.whiteBackgroundBright(testString), testString);
    }

}