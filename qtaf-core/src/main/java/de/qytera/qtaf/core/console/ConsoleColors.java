package de.qytera.qtaf.core.console;

import de.qytera.qtaf.core.QtafFactory;

/**
 * Class that generates colored console outputs
 */
public class ConsoleColors {

    /**
     * RESET.
     *
     */
    public static final String RESET = "\033[0m";

    
    /**
     * BLACK.
     */
    public static final String BLACK = "\033[0;30m";
    /**
     * RED.
     */
    public static final String RED = "\033[0;31m";
    /**
     * GREEN.
     */
    public static final String GREEN = "\033[0;32m";
    /**
     * YELLOW.
     */
    public static final String YELLOW = "\033[0;33m";
    /**
     * BLUE.
     */
    public static final String BLUE = "\033[0;34m";
    /**
     * PURPLE.
     */
    public static final String PURPLE = "\033[0;35m";
    /**
     * CYAN.
     */
    public static final String CYAN = "\033[0;36m";
    /**
     * WHITE.
     */
    public static final String WHITE = "\033[0;37m";

    
    /**
     * BLACK_BOLD.
     */
    public static final String BLACK_BOLD = "\033[1;30m";
    /**
     * RED_BOLD.
     */
    public static final String RED_BOLD = "\033[1;31m";
    /**
     * GREEN_BOLD.
     */
    public static final String GREEN_BOLD = "\033[1;32m";
    /**
     * YELLOW_BOLD.
     */
    public static final String YELLOW_BOLD = "\033[1;33m";
    /**
     * BLUE_BOLD.
     */
    public static final String BLUE_BOLD = "\033[1;34m";
    /**
     * PURPLE_BOLD.
     */
    public static final String PURPLE_BOLD = "\033[1;35m";
    /**
     * CYAN_BOLD.
     */
    public static final String CYAN_BOLD = "\033[1;36m";
    /**
     * WHITE_BOLD.
     */
    public static final String WHITE_BOLD = "\033[1;37m";

    
    /**
     * BLACK_UNDERLINED.
     */
    public static final String BLACK_UNDERLINED = "\033[4;30m";
    /**
     * RED_UNDERLINED.
     */
    public static final String RED_UNDERLINED = "\033[4;31m";
    /**
     * GREEN_UNDERLINED.
     */
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    /**
     * YELLOW_UNDERLINED.
     */
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    /**
     * BLUE_UNDERLINED.
     */
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    /**
     * PURPLE_UNDERLINED.
     */
    public static final String PURPLE_UNDERLINED = "\033[4;35m";
    /**
     * CYAN_UNDERLINED.
     */
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    /**
     * WHITE_UNDERLINED.
     */
    public static final String WHITE_UNDERLINED = "\033[4;37m";

    
    /**
     * BLACK_BACKGROUND.
     */
    public static final String BLACK_BACKGROUND = "\033[40m";
    /**
     * RED_BACKGROUND.
     */
    public static final String RED_BACKGROUND = "\033[41m";
    /**
     * GREEN_BACKGROUND.
     */
    public static final String GREEN_BACKGROUND = "\033[42m";
    /**
     * YELLOW_BACKGROUND.
     */
    public static final String YELLOW_BACKGROUND = "\033[43m";
    /**
     * BLUE_BACKGROUND.
     */
    public static final String BLUE_BACKGROUND = "\033[44m";
    /**
     * PURPLE_BACKGROUND.
     */
    public static final String PURPLE_BACKGROUND = "\033[45m";
    /**
     * CYAN_BACKGROUND.
     */
    public static final String CYAN_BACKGROUND = "\033[46m";
    /**
     * WHITE_BACKGROUND.
     */
    public static final String WHITE_BACKGROUND = "\033[47m";

    
    /**
     * BLACK_BRIGHT.
     */
    public static final String BLACK_BRIGHT = "\033[0;90m";
    /**
     * RED_BRIGHT.
     */
    public static final String RED_BRIGHT = "\033[0;91m";
    /**
     * GREEN_BRIGHT.
     */
    public static final String GREEN_BRIGHT = "\033[0;92m";
    /**
     * YELLOW_BRIGHT.
     */
    public static final String YELLOW_BRIGHT = "\033[0;93m";
    /**
     * BLUE_BRIGHT.
     */
    public static final String BLUE_BRIGHT = "\033[0;94m";
    /**
     * PURPLE_BRIGHT.
     */
    public static final String PURPLE_BRIGHT = "\033[0;95m";
    /**
     * CYAN_BRIGHT.
     */
    public static final String CYAN_BRIGHT = "\033[0;96m";
    /**
     * WHITE_BRIGHT.
     */
    public static final String WHITE_BRIGHT = "\033[0;97m";

    
    /**
     * BLACK_BOLD_BRIGHT.
     */
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";
    /**
     * RED_BOLD_BRIGHT.
     */
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    /**
     * GREEN_BOLD_BRIGHT.
     */
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    /**
     * YELLOW_BOLD_BRIGHT.
     */
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    /**
     * BLUE_BOLD_BRIGHT.
     */
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    /**
     * PURPLE_BOLD_BRIGHT.
     */
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
    /**
     * CYAN_BOLD_BRIGHT.
     */
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
    /**
     * WHITE_BOLD_BRIGHT.
     */
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";

    
    /**
     * BLACK_BACKGROUND_BRIGHT.
     */
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";
    /**
     * RED_BACKGROUND_BRIGHT.
     */
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";
    /**
     * GREEN_BACKGROUND_BRIGHT.
     */
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";
    /**
     * YELLOW_BACKGROUND_BRIGHT.
     */
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";
    /**
     * BLUE_BACKGROUND_BRIGHT.
     */
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";
    /**
     * PURPLE_BACKGROUND_BRIGHT.
     */
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m";
    /**
     * CYAN_BACKGROUND_BRIGHT.
     */
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";
    /**
     * WHITE_BACKGROUND_BRIGHT.
     */
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";

    /**
     * Check whether colors are allowed to be used.
     *
     * @return  true if colors in console output are allowed, false otherwise.
     */
    public static boolean enabled() {
        return QtafFactory.getConfiguration().getBoolean("console.colors", true);
    }

    /**
     * Black text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String black(String s) {
        return enabled() ? BLACK + s + RESET : s;
    }

    /**
     * Red text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String red(String s) {
        return enabled() ? RED + s + RESET : s;
    }

    /**
     * Green text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String green(String s) {
        return enabled() ? GREEN + s + RESET : s;
    }

    /**
     * Yellow text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String yellow(String s) {
        return enabled() ? YELLOW + s + RESET : s;
    }

    /**
     * Blue text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blue(String s) {
        return enabled() ? BLUE + s + RESET : s;
    }

    /**
     * Purple text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String purple(String s) {
        return enabled() ? PURPLE + s + RESET : s;
    }

    /**
     * Cyan text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String cyan(String s) {
        return enabled() ? CYAN + s + RESET : s;
    }

    /**
     * White text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String white(String s) {
        return enabled() ? WHITE + s + RESET : s;
    }

    /**
     * Black bold text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blackBold(String s) {
        return enabled() ? BLACK_BOLD + s + RESET : s;
    }

    /**
     * Red bold text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String redBold(String s) {
        return enabled() ? RED_BOLD + s + RESET : s;
    }

    /**
     * Green bold text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String greenBold(String s) {
        return enabled() ? GREEN_BOLD + s + RESET : s;
    }

    /**
     * Yellow bold text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String yellowBold(String s) {
        return enabled() ? YELLOW_BOLD + s + RESET : s;
    }

    /**
     * Blue bold text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blueBold(String s) {
        return enabled() ? BLUE_BOLD + s + RESET : s;
    }

    /**
     * Purple bold text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String purpleBold(String s) {
        return enabled() ? PURPLE_BOLD + s + RESET : s;
    }

    /**
     * Cyan bold text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String cyanBold(String s) {
        return enabled() ? CYAN_BOLD + s + RESET : s;
    }

    /**
     * White bold text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String whiteBold(String s) {
        return enabled() ? WHITE_BOLD + s + RESET : s;
    }

    /**
     * Black underlined text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blackUnderlined(String s) {
        return enabled() ? BLACK_UNDERLINED + s + RESET : s;
    }

    /**
     * Red underlined text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String redUnderlined(String s) {
        return enabled() ? RED_UNDERLINED + s + RESET : s;
    }

    /**
     * Green underlined text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String greenUnderlined(String s) {
        return enabled() ? GREEN_UNDERLINED + s + RESET : s;
    }

    /**
     * Yellow underlined text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String yellowUnderlined(String s) {
        return enabled() ? YELLOW_UNDERLINED + s + RESET : s;
    }

    /**
     * Blue underlined text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blueUnderlined(String s) {
        return enabled() ? BLUE_UNDERLINED + s + RESET : s;
    }

    /**
     * Purple underlined text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String purpleUnderlined(String s) {
        return enabled() ? PURPLE_UNDERLINED + s + RESET : s;
    }

    /**
     * Cyan underlined text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String cyanUnderlined(String s) {
        return enabled() ? CYAN_UNDERLINED + s + RESET : s;
    }

    /**
     * White underlined text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String whiteUnderlined(String s) {
        return enabled() ? WHITE_UNDERLINED + s + RESET : s;
    }

    /**
     * Black background
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blackBg(String s) {
        return enabled() ? BLACK_BACKGROUND + s + RESET : s;
    }

    /**
     * Red background
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String redBg(String s) {
        return enabled() ? RED_BACKGROUND + s + RESET : s;
    }

    /**
     * Green background
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String greenBg(String s) {
        return enabled() ? GREEN_BACKGROUND + s + RESET : s;
    }

    /**
     * Yellow background
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String yellowBg(String s) {
        return enabled() ? YELLOW_BACKGROUND + s + RESET : s;
    }

    /**
     * Blue background
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blueBg(String s) {
        return enabled() ? BLUE_BACKGROUND + s + RESET : s;
    }

    /**
     * Purple background
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String purpleBg(String s) {
        return enabled() ? PURPLE_BACKGROUND + s + RESET : s;
    }

    /**
     * Cyan background
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String cyanBg(String s) {
        return enabled() ? CYAN_BACKGROUND + s + RESET : s;
    }

    /**
     * White background
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String whiteBg(String s) {
        return enabled() ? WHITE_BACKGROUND + s + RESET : s;
    }

    /**
     * Black bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blackBright(String s) {
        return enabled() ? BLACK_BRIGHT + s + RESET : s;
    }

    /**
     * Red bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String redBright(String s) {
        return enabled() ? RED_BRIGHT + s + RESET : s;
    }

    /**
     * Green bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String greenBright(String s) {
        return enabled() ? GREEN_BRIGHT + s + RESET : s;
    }

    /**
     * Yellow bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String yellowBright(String s) {
        return enabled() ? YELLOW_BRIGHT + s + RESET : s;
    }

    /**
     * Blue bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blueBright(String s) {
        return enabled() ? BLUE_BRIGHT + s + RESET : s;
    }

    /**
     * Purple bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String purpleBright(String s) {
        return enabled() ? PURPLE_BRIGHT + s + RESET : s;
    }

    /**
     * Cyan bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String cyanBright(String s) {
        return enabled() ? CYAN_BRIGHT + s + RESET : s;
    }

    /**
     * White bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String whiteBright(String s) {
        return enabled() ? WHITE_BRIGHT + s + RESET : s;
    }

    /**
     * Black bold bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blackBoldBright(String s) {
        return enabled() ? BLACK_BOLD_BRIGHT + s + RESET : s;
    }

    /**
     * Red bold bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String redBoldBright(String s) {
        return enabled() ? RED_BOLD_BRIGHT + s + RESET : s;
    }

    /**
     * Green bold bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String greenBoldBright(String s) {
        return enabled() ? GREEN_BOLD_BRIGHT + s + RESET : s;
    }

    /**
     * Yellow bold bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String yellowBoldBright(String s) {
        return enabled() ? YELLOW_BOLD_BRIGHT + s + RESET : s;
    }

    /**
     * Blue bold bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blueBoldBright(String s) {
        return enabled() ? BLUE_BOLD_BRIGHT + s + RESET : s;
    }

    /**
     * Purple bold bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String purpleBoldBright(String s) {
        return enabled() ? PURPLE_BOLD_BRIGHT + s + RESET : s;
    }

    /**
     * Cyan bold bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String cyanBoldBright(String s) {
        return enabled() ? CYAN_BOLD_BRIGHT + s + RESET : s;
    }

    /**
     * White bold bright text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String whiteBoldBright(String s) {
        return enabled() ? WHITE_BOLD_BRIGHT + s + RESET : s;
    }

    /**
     * Black bright background text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blackBackgroundBright(String s) {
        return enabled() ? BLACK_BACKGROUND_BRIGHT + s + RESET : s;
    }

    /**
     * Red bright background text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String redBackgroundBright(String s) {
        return enabled() ? RED_BACKGROUND_BRIGHT + s + RESET : s;
    }

    /**
     * Green bright background text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String greenBackgroundBright(String s) {
        return enabled() ? GREEN_BACKGROUND_BRIGHT + s + RESET : s;
    }

    /**
     * Yellow bright background text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String yellowBackgroundBright(String s) {
        return enabled() ? YELLOW_BACKGROUND_BRIGHT + s + RESET : s;
    }

    /**
     * Blue bright background text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String blueBackgroundBright(String s) {
        return enabled() ? BLUE_BACKGROUND_BRIGHT + s + RESET : s;
    }

    /**
     * Purple bright background text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String purpleBackgroundBright(String s) {
        return enabled() ? PURPLE_BACKGROUND_BRIGHT + s + RESET : s;
    }

    /**
     * Cyan bright background text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String cyanBackgroundBright(String s) {
        return enabled() ? CYAN_BACKGROUND_BRIGHT + s + RESET : s;
    }

    /**
     * White bright background text.
     *
     * @param s text
     * @return  text wrapped in color tags
     */
    public static String whiteBackgroundBright(String s) {
        return enabled() ? WHITE_BACKGROUND_BRIGHT + s + RESET : s;
    }
}