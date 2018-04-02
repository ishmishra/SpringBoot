package regEx;

import org.junit.Test;

/**
 * Character classes and boundary matchers
 */
public class CharacterClassesAndBoundaryMatchers {

    @Test
    public void simpleRegEx() {

        String myStr = "Cat got my tongue";
        System.out.println(myStr);

        String yourString = myStr.replaceAll("C", "B");
        System.out.println(yourString);

        String alphaNumeric = "123abcDEFghI345eIiK";
        System.out.println(alphaNumeric.replaceAll(".", "Y"));


        //Character boundary matcher
        System.out.println(alphaNumeric.replaceAll("^123ab", "Beggining"));


        //Check if regex matches as a whole
        System.out.println(alphaNumeric.matches("^123abcD"));

        System.out.println(alphaNumeric.replaceAll("345$", "endMatcher"));

        //replace all occurrences of a,e and i
        System.out.println(alphaNumeric.replaceAll("[aei]", "X"));

        System.out.println(alphaNumeric.replaceAll("[aie]", "REPLACED"));

        //replace a,e,or i followed by b or K
        System.out.println(alphaNumeric.replaceAll("[aei][bK]", "replaceFollowedBy"));

        //use range a to e and 1-3
        System.out.println(alphaNumeric.replaceAll("[a-e1-3]", "X"));

        //include range and uppercase
        System.out.println(alphaNumeric.replaceAll("(?i)[a-e1-3]", "X"));

        //replace numbers
        System.out.println(alphaNumeric.replaceAll("\\d", "Z"));
        System.out.println(alphaNumeric.replaceAll("\\D", "Z"));
        System.out.println(alphaNumeric.replaceAll("[0-9]", "Z"));

        //to remove white spaces
        System.out.println(myStr.replaceAll("\\s", ""));

        //except whitespace whitespace
        System.out.println(myStr.replaceAll("\\w", "T"));

        ///
        System.out.println(myStr.replaceAll("\\b", "E"));

    }


}
