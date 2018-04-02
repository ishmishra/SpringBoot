package regEx;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuantifiersAndMatchers {

    @Test
    public void testQuantifiers() {

        String alphaNum = "abcdeeefGHIJK123xYzabcd";
        String alphaNumWithoutE = "abcdfGHHHIJK123xYzabcd";

        System.out.println(alphaNum.replaceAll("^abcdeee", "Y"));

        //One can use quantifiers after character which repeats
        System.out.println(alphaNum.replaceAll("^abcde{3}", "Y"));

        //if you dont want to specify the number of characters and only that multiple repeate
        System.out.println(alphaNum.replaceAll("^abcde+", "Y"));

        //for optional e
        System.out.println(alphaNumWithoutE.replaceAll("^abcde*+", "Y"));

        //multiple H followed by optional I ,followed by J
        System.out.println(alphaNumWithoutE.replaceAll("H+I*J", "Q"));

    }

    @Test
    public void testMatchers() {
        String htmlIndex = "<style type=\"text/css\">.trc_related_container div[data-item-syndicated=\"true\"]," +
                " .trc_rbox_div .syndicatedItem, .trc_rbox_border_elm .syndicatedItem, .trc_rbox .syndicatedItem, " +
                ".commerce-inset, a[href*=\".trust.zone\"], iframe[src^=\"http://ad.yieldmanager.com/\"]," +
                " iframe[id^=\"google_ads_iframe\"], iframe[id^=\"google_ads_frame\"], div[itemtype=\"http:" +
                "//schema.org/WPAdBlock\"], div[id^=\"div-gpt-ad\"], div[id^=\"dfp-ad-\"], div[id^=\"advads-\"], " +
                "div[id^=\"MarketGid\"], a[onmousedown^=\"this.href='https://paid.outbrain.com/network/redir?\"]" +
                "[target=\"_blank\"], a[href^=\"https://ad.doubleclick.net/\"], a[href^=\"http://www.yourfuckbook.com/?\"]," +
                " a[href^=\"http://www.socialsx.com/\"], a[href^=\"http://www.liutilities.com/\"]";

        String divPattern = "div";

        Pattern pattern = Pattern.compile(divPattern);
        Matcher matcher = pattern.matcher(htmlIndex);

        //You can use matches only once
        //System.out.println(matcher.matches());

        int count = 0;
//        while (matcher.find()) {
//
//            ++count;
//            System.out.println("Occurrence: " + count + " : " + matcher.start() + " to " + matcher.end());
//        }


        matcher.reset();
        System.out.println("harry".replaceAll("H|h", "K"));

        matcher = Pattern.compile("t(?!v)").matcher("tstvtkt");

        while (matcher.find()) {

            ++count;
            System.out.println("Occurrence: " + count + " : " + matcher.start() + " to " + matcher.end());
        }


    }
}
