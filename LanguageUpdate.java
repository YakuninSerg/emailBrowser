package emailbrouser;

import java.util.HashMap;
import java.util.Map;

public class LanguageUpdate {
    private static Map<String, String> charMap = new HashMap<>();

    static {
        charMap.put("а", "a");
        charMap.put("б", "b");
        charMap.put("в", "v");
        charMap.put("г", "g");
        charMap.put("д", "d");
        charMap.put("е", "e");
        charMap.put("ё", "yo");
        charMap.put("ж", "j");
        charMap.put("з", "z");
        charMap.put("и", "i");
        charMap.put("й", "y");
        charMap.put("к", "k");
        charMap.put("л", "l");
        charMap.put("м", "m");
        charMap.put("н", "n");
        charMap.put("о", "o");
        charMap.put("п", "p");
        charMap.put("р", "r");
        charMap.put("с", "s");
        charMap.put("т", "t");
        charMap.put("у", "u");
        charMap.put("ф", "f");
        charMap.put("х", "h");
        charMap.put("ц", "c");
        charMap.put("ч", "ch");
        charMap.put("ш", "sh");
        charMap.put("щ", "sch");
        charMap.put("ъ", "'");
        charMap.put("ы", "y");
        charMap.put("ь", "'");
        charMap.put("э", "e");
        charMap.put("ю", "yu");
        charMap.put("я", "ya");

    }

    static String changeRussianOnEnglish(String str) {
        String result = str.toLowerCase();
        result = result.replace("-", "");
        char[] chars = result.toCharArray();
        for (char c : chars) {
            String oldChar = String.valueOf(c);
            String newChar = charMap.get(oldChar);
            if (newChar != null) {
                result = result.replace(oldChar, newChar);
            }
        }
        return result;
    }

  
}
