import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
/*
Every time a new file is opened, the following occur in sequence:
    A truecopy of the file is created
    removeComments()
    removeWhitespaces()
    Scan the file and :
        Add #includes recursively using writeInclude()
        Create #define data table using storeDefine()
        Add #define constants using putDefineConst()
        Add #define macros using putDefineFunc()

Detailed explanation of each function given in it.
I know the program is way too long, but can't shorten it anymore.
*/

class DefineFunc {
    /* For #define macros,to distinguish between different types of binary macros */
    String var1, var2, operator;
    int varType1, varType2;

    public DefineFunc(String var1, String var2, String operator, int varType1, int varType2) {
        this.var1 = var1;
        this.var2 = var2;
        this.operator = operator;
        this.varType1 = varType1;
        this.varType2 = varType2;
    }
}

public class Preprocessor {
    private static final String fileName = "xyz.c";
    private static final String PATH = "E:/Coding/Others/Java/EEM514/Assignment2/";
    // set to empty for relative address

    public static void createCopy(String fileName, String copyFileName) throws IOException {
        /* Makes a copy of file */

        FileReader fileRead = new FileReader(PATH + fileName);
        FileWriter fileWrite = new FileWriter(PATH + copyFileName);
        BufferedReader reader = new BufferedReader(fileRead);
        BufferedWriter writer = new BufferedWriter(fileWrite);

        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }
        reader.close();
        writer.close();
    }

    public static ArrayList<Integer> getQuotePos(String line) {
        /* Stores start and end indices of all quotes */
        ArrayList<Integer> quotePos = new ArrayList<Integer>();
        boolean quote = false;

        for (int pos = 0; pos < line.length(); pos++) {
            if (quote) {
                if (line.charAt(pos) == '\"') {
                    if (!(pos > 0 && line.charAt(pos - 1) == '\\')) {
                        quote = false;
                        quotePos.add(pos);
                    }
                }
            } else {
                if (line.charAt(pos) == '\"') {
                    quote = true;
                    quotePos.add(pos);
                }
            }
        }
        return quotePos;
    }

    public static void removeWhitespaces(String fileName) throws IOException {
        /* Removes all whitespaces, only call after removeComments */
        /*
         * Logic: First finds positon of all valid quotes. Then removes whitespaces in
         * not quoted text and copies quoted text as it is.
         */
        createCopy(fileName, "temp" + fileName);

        File file = new File(PATH + fileName);
        File tempFile = new File(PATH + "temp" + fileName);
        FileReader fileRead = new FileReader(tempFile);
        FileWriter fileWrite = new FileWriter(file);
        BufferedReader reader = new BufferedReader(fileRead);
        BufferedWriter writer = new BufferedWriter(fileWrite);

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            String newLine = "";
            ArrayList<Integer> quotePos = getQuotePos(line);

            if (quotePos.size() > 0) {
                String tempLine = line.substring(0, quotePos.get(0));
                newLine = tempLine.replaceAll("\\s+", " ");

                for (int i = 0; i < quotePos.size(); i += 2) {
                    // inside quotes exactly copied
                    newLine = newLine + line.substring(quotePos.get(i), quotePos.get(i + 1) + 1);
                    if (i + 2 < quotePos.size()) {
                        // outside quotes whitespaces removed
                        tempLine = line.substring(quotePos.get(i + 1) + 1, quotePos.get(i + 2));
                        newLine = newLine + tempLine.replaceAll("\\s+", " ");
                    }
                }

                if (quotePos.get(quotePos.size() - 1) < line.length() - 1) {
                    tempLine = line.substring(quotePos.get(quotePos.size() - 1) + 1);
                    newLine = newLine + tempLine.replaceAll("\\s+", " ");
                }
            } else {// no quotes case
                newLine = line.replaceAll("\\s+", " ");
            }

            writer.write(newLine);
            writer.newLine();
        }
        reader.close();
        writer.close();
        tempFile.delete();
    }

    public static void removeComments(String fileName) throws IOException {
        /* Removes all C-style comments */
        /*
         * Logic: Copies non-commented text using double boolean variable loop. Replaces
         * comment by a space. Comments can't exist in a Quote and vice-versa. Backslash
         * can only be inside a quote.
         */
        createCopy(fileName, "temp" + fileName);

        File file = new File(PATH + fileName);
        File tempFile = new File(PATH + "temp" + fileName);
        FileReader fileRead = new FileReader(tempFile);
        FileWriter fileWrite = new FileWriter(file);
        BufferedReader reader = new BufferedReader(fileRead);
        BufferedWriter writer = new BufferedWriter(fileWrite);
        String line;
        boolean quote = false, comment = false;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            int pos = 0, lineLen = line.length();
            String newLine = "";

            while (pos < lineLen) {
                if (!comment) {
                    if (quote) {
                        if (line.charAt(pos) == '\"') {
                            if (!(pos > 0 && line.charAt(pos - 1) == '\\')) {
                                quote = false;
                            }
                        }
                    } else {
                        if (line.charAt(pos) == '\"') {
                            quote = true;
                        } else if (line.charAt(pos) == '/') {
                            if (pos + 1 < lineLen && line.charAt(pos + 1) == '*') {
                                comment = true;
                            }
                        }
                    }
                    if (!comment) {
                        newLine = newLine + line.charAt(pos);
                    }
                } else {
                    if (line.charAt(pos) == '*') {
                        if (pos + 1 < lineLen && line.charAt(pos + 1) == '/') {
                            comment = false;
                            newLine = newLine + " ";
                            pos++;
                        }
                    }
                }
                pos++;
            }

            writer.write(newLine);
            if (!comment) { // to convert multi-line comments into a single line
                writer.newLine();
            }
        }
        reader.close();
        writer.close();
        tempFile.delete();
    }

    public static void storeDefine(String line, Hashtable<String, String> defineTableConst,
            Hashtable<String, DefineFunc> defineTableFunc) {
        /* Stores all #define constants and macros in hashtables */
        /*
         * Logic: Searches for a #define, if found then takes the const(or macro) name
         * (uses'('to check if const or macro). Only for binary macros, might not work
         * if quotes are present in macros. For further explaination look below.
         */
        int i = line.indexOf("define") + 7;
        char chr;
        String str = "", expr = "", value = "";

        while ((chr = line.charAt(i)) != ' ' && chr != ')') {
            str = str + chr;
            i++;
        }

        if (str.indexOf('(') == -1) {
            // constants
            for (i = i + 1; i < line.length(); i++) {
                value = value + line.charAt(i);
            }
            defineTableConst.put(str, value);
            System.out.println("Added: #define " + str);//
        } else {
            // macros
            /*
             * Logic: First seperates the macro part and its replacement. Then seperates the
             * macro name. Then seperates its variables(these contain 1 braces set). Then
             * seperates the expression variables(these can contain multiple braces set) and
             * the binary operator. Finally we check which kind of macro it is: a,b; b,a;
             */
            while ((chr = line.charAt(i)) != ')') {
                str = str + chr;
                i++;
            }
            str = str + chr;
            i++;
            str = str.replaceAll("\\s+", ""); // str stores the name along with variables of macro

            String name = "", var1 = "", var2 = "", expr1 = "", expr2 = "", v1 = "", v2 = "";
            String operators = "+*-/%><=!&|~^", operator = "";
            int pos = 0, varType1, varType2;

            while (str.charAt(pos) != '(') {
                name = name + str.charAt(pos);
                pos++;
            }
            pos++;
            while (str.charAt(pos) != ',') {
                var1 = var1 + str.charAt(pos);
                pos++;
            }
            pos++;
            while (str.charAt(pos) != ')') {
                var2 = var2 + str.charAt(pos);
                pos++;
            }
            pos++;

            for (; i < line.length(); i++) {
                chr = line.charAt(i);
                expr = expr + chr;
            }
            expr = expr.replaceAll("\\s+", "");
            expr = expr.replace("(", "");
            expr = expr.replace(")", "");
            pos = 0;
            while (operators.indexOf(expr.charAt(pos)) == -1) {
                expr1 = expr1 + expr.charAt(pos);
                pos++;
            }
            while (!(operators.indexOf(expr.charAt(pos)) == -1)) {
                operator = operator + expr.charAt(pos);
                pos++;
            }
            for (; pos < expr.length(); pos++) {
                expr2 = expr2 + expr.charAt(pos);
            }

            if (expr1.equals(var1)) {
                varType1 = 1;
            } else if (expr1.equals(var2)) {
                varType1 = 2;
            } else {
                varType1 = 0;
                if (defineTableConst.containsKey(expr1)) {
                    v1 = "" + defineTableConst.get(expr1);// to force String type
                } else {
                    v1 = expr1;
                }
            }
            if (expr2.equals(var2)) {
                varType2 = 2;
            } else if (expr2.equals(var1)) {
                varType2 = 1;
            } else {
                varType2 = 0;
                if (defineTableConst.containsKey(expr2)) {
                    v2 = "" + defineTableConst.get(expr2);// to force String type
                } else {
                    v2 = expr2;
                }
            }

            DefineFunc obj = new DefineFunc(v1, v2, operator, varType1, varType2);
            defineTableFunc.put(name, obj);
            System.out.println("Added: #define " + name + "()");//
        }
    }

    public static String _replaceDefineConst(String line, Hashtable<String, String> defineTableConst) {
        /* Works with putDefineConst(), do not use seperately */
        /*
         * Logic: Finds for #define const symbols in non-quoted area. If found, replaces
         * it with its value and return the edited line with a space at start. This
         * space tells that putDefineConst should be called again for this line. Thus
         * the same line is called repeatedly until all of its consts are replaced with
         * values.
         */

        Enumeration<String> keys = defineTableConst.keys();
        String key, value;
        int index, keyLen;

        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            keyLen = key.length();
            value = defineTableConst.get(key);
            index = 0;

            while (index < line.length()) {
                index = line.indexOf(key, index);
                if (index != -1) {
                    if (index > 0 && line.substring(index - 1, index).matches("\\w")) {
                        index += keyLen;
                    } else if ((index + keyLen) < (line.length() - 1)
                            && line.substring(index + keyLen, index + keyLen + 1).matches("\\w")) {
                        index += keyLen;
                    } else {
                        line = line.substring(0, index) + value + line.substring(index + keyLen);
                        return (" " + line);
                    }
                } else {
                    break;
                }
            }
        }
        return line;
    }

    public static String _replaceDefineFunc(String line, Hashtable<String, DefineFunc> defineTableFunc) {
        /* Works with putDefineFunc(), do not use seperately */
        /*
         * Logic: Finds for #define macros symbols in non-quoted area. If found,
         * replaces it with its expression and return the edited line with a space at
         * start. This space tells that putDefineFunc should be called again for this
         * line. Thus the same line is called repeatedly until all of its macros are
         * replaced with their expressions.
         */

        Enumeration<String> keys = defineTableFunc.keys();
        String key, str1 = "", str2 = "", rplcString = "";
        int index, index2, bracketCount, keyLen, v1, v2;
        DefineFunc obj;
        char chr;

        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            keyLen = key.length();
            obj = defineTableFunc.get(key);
            index = 0;

            while (index < line.length()) {
                index = line.indexOf(key, index);
                if (index != -1) {
                    if (index > 0 && line.substring(index - 1, index).matches("\\w")) {
                        index += keyLen;

                    } else if ((index + keyLen) < line.length()
                            && line.substring(index + keyLen, index + keyLen + 1).matches("\\w")) {
                        index += keyLen;

                    } else if (((index + keyLen) < (line.length() - 1))
                            && (line.substring(index + keyLen, index + keyLen + 2).equals(" (")
                                    || line.charAt(index + keyLen) == '(')) {
                        index2 = index;
                        while (line.charAt(index) != '(') {
                            index++;
                        }
                        index++;

                        bracketCount = 0;
                        while ((chr = line.charAt(index)) != ',' || bracketCount != 0) {
                            if (chr == '(') {
                                bracketCount++;
                            } else if (chr == ')') {
                                bracketCount--;
                            }
                            if (bracketCount < 0) {
                                break;
                            }
                            str1 = str1 + chr;
                            index++;
                        }
                        str1 = str1.trim();
                        index++;

                        while (bracketCount >= 0) {
                            chr = line.charAt(index);
                            if (chr == '(') {
                                bracketCount++;
                            } else if (chr == ')') {
                                bracketCount--;
                            }
                            if (bracketCount < 0) {
                                break;
                            }
                            str2 = str2 + chr;
                            index++;
                        }
                        str2 = str2.trim();

                        v1 = obj.varType1;
                        v2 = obj.varType2;
                        if (v1 == 1) {
                            rplcString = rplcString + str1;
                        } else if (v1 == 2) {
                            rplcString = rplcString + str2;
                        } else if (v1 == 0) {
                            rplcString = rplcString + obj.var1;
                        }
                        rplcString = rplcString + obj.operator;
                        if (v2 == 1) {
                            rplcString = rplcString + str1;
                        } else if (v2 == 2) {
                            rplcString = rplcString + str2;
                        } else if (v2 == 0) {
                            rplcString = rplcString + obj.var2;
                        }

                        line = line.substring(0, index2) + rplcString + line.substring(index + 1);
                        return " " + line;
                    }
                } else {
                    break;
                }
            }
        }
        return line;
    }

    public static String putDefine(String line, Hashtable<String, String> defineTableConst,
            Hashtable<String, DefineFunc> defineTableFunc, String type) {
        /* Replaces #define constants and macros with their value */
        /*
         * Logic: Finds #define constants and macros in the non-quoted area. For more
         * info look into _replaceDefineConst() and _replaceDefineFunc().
         */
        String newLine = "", tempLine;
        line = line.trim();
        ArrayList<Integer> quotePos = getQuotePos(line);
        boolean flag = false;
        if (type.equals("const")) {
            flag = true;
        }

        if (quotePos.size() > 0) {
            tempLine = line.substring(0, quotePos.get(0));
            if (flag) {
                tempLine = _replaceDefineConst(tempLine, defineTableConst);
            } else {
                tempLine = _replaceDefineFunc(tempLine, defineTableFunc);
            }
            if (tempLine.charAt(0) == ' ') {
                return " " + tempLine.substring(1) + line.substring(quotePos.get(0));
            } else {
                newLine = newLine + tempLine;
            }

            for (int i = 0; i < quotePos.size(); i += 2) {
                newLine = newLine + line.substring(quotePos.get(i), quotePos.get(i + 1) + 1);
                if (i + 2 < quotePos.size()) {
                    tempLine = line.substring(quotePos.get(i + 1) + 1, quotePos.get(i + 2));
                    if (flag) {
                        tempLine = _replaceDefineConst(tempLine, defineTableConst);
                    } else {
                        tempLine = _replaceDefineFunc(tempLine, defineTableFunc);
                    }
                    if (tempLine.charAt(0) == ' ') {
                        return " " + newLine + tempLine.substring(1) + line.substring(quotePos.get(i + 2));
                    } else {
                        newLine = newLine + tempLine;
                    }
                }
            }

            if (quotePos.get(quotePos.size() - 1) < line.length() - 1) {
                tempLine = line.substring(quotePos.get(quotePos.size() - 1) + 1);
                if (flag) {
                    tempLine = _replaceDefineConst(tempLine, defineTableConst);
                } else {
                    tempLine = _replaceDefineFunc(tempLine, defineTableFunc);
                }
                if (tempLine.charAt(0) == ' ') {
                    return " " + newLine + tempLine.substring(1);
                } else {
                    newLine = newLine + tempLine;
                }
            }
        } else {// no quotes case
            tempLine = line;
            if (flag) {
                tempLine = _replaceDefineConst(tempLine, defineTableConst);
            } else {
                tempLine = _replaceDefineFunc(tempLine, defineTableFunc);
            }
            if (tempLine.charAt(0) == ' ') {
                return tempLine;
            } else {
                newLine = newLine + tempLine;
            }
        }
        return newLine;
    }

    public static void writeInclude(String str, BufferedWriter writer, ArrayList<String> headerList,
            ArrayList<String> trueFileList) throws IOException {
        /* To add #include files data */
        /*
         * Logic: Looks for headerFile name in quotes. If accessed for the first time:
         * Adds to trueFileList, Creates a trueCopy, Removes Comments, Removes
         * Whitespaces HeaderList has names of headerfiles already included to prevent
         * recursion. Then adds the headerfile into the main file.
         */

        int st = str.indexOf("\"");// finds name of header file enclosed in ""
        int en = str.indexOf("\"", st + 1);
        String headerFile = str.substring(st + 1, en).trim(); // this works for " one.h" but it should not
        BufferedReader reader = null;

        if (!(trueFileList.contains(headerFile))) {
            System.out.println("Included: " + headerFile);//
            createCopy(headerFile, "true" + headerFile);
            trueFileList.add(headerFile);
            removeComments(headerFile);
            removeWhitespaces(headerFile);
        }

        if (!headerList.contains(headerFile)) {
            headerList.add(headerFile);
            FileReader fileRead = new FileReader(PATH + headerFile);
            reader = new BufferedReader(fileRead);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.charAt(0) == '#' && (line.indexOf("include") == 1 || line.indexOf("include") == 2)) {
                        // Problem: works even if there is garbage b/w include and "" but is more
                        // reliable
                        writeInclude(line, writer, headerList, trueFileList);
                    } else {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = null;
        BufferedWriter writer = null;
        FileReader fileRead;
        FileWriter fileWrite;
        ArrayList<String> trueFileList = new ArrayList<String>();
        ArrayList<String> headerList = new ArrayList<String>();
        Hashtable<String, String> defineTableConst = new Hashtable<String, String>();
        Hashtable<String, DefineFunc> defineTableFunc = new Hashtable<String, DefineFunc>();
        String line;

        System.out.println("Opened File: " + fileName);//
        createCopy(fileName, "true" + fileName);
        trueFileList.add(fileName);
        removeComments(fileName);
        removeWhitespaces(fileName);

        createCopy(fileName, "temp" + fileName);
        File tempFile = new File(PATH + "temp" + fileName);
        File mainFile = new File(PATH + fileName);

        /* Deals with #includes */
        fileRead = new FileReader(tempFile);
        fileWrite = new FileWriter(mainFile);
        reader = new BufferedReader(fileRead);
        writer = new BufferedWriter(fileWrite);
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                if (line.charAt(0) == '#' && (line.indexOf("include") == 1 || line.indexOf("include") == 2)) {
                    // ArrayList<String> headerList = new ArrayList<String>();//do not use this
                    writeInclude(line, writer, headerList, trueFileList);
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        reader.close();
        writer.close();

        /* Stores #defines */
        createCopy(fileName, "temp" + fileName);
        fileRead = new FileReader(tempFile);
        fileWrite = new FileWriter(mainFile);
        reader = new BufferedReader(fileRead);
        writer = new BufferedWriter(fileWrite);
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                if (line.charAt(0) == '#' && (line.indexOf("define") == 1 || line.indexOf("define") == 2)) {
                    storeDefine(line, defineTableConst, defineTableFunc);
                } else{
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        reader.close();
        writer.close();
        tempFile.delete();

        /* Puts Consts and Macros */
        createCopy(fileName, "temp" + fileName);
        fileRead = new FileReader(tempFile);
        fileWrite = new FileWriter(mainFile);
        reader = new BufferedReader(fileRead);
        writer = new BufferedWriter(fileWrite);
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                do {
                    line = putDefine(line, defineTableConst, defineTableFunc, "const");
                } while (line.charAt(0) == ' ');
                do {
                    line = putDefine(line, defineTableConst, defineTableFunc, "macro");
                } while (line.charAt(0) == ' ');
                writer.write(line);
                writer.newLine();
            }
        }
        reader.close();
        writer.close();
        tempFile.delete();

        createCopy(fileName, "final" + fileName);
        System.out.println("\nConversion Completed\nResult in File: final" + fileName);//
        for (int i = 0; i < trueFileList.size(); i++) {
            /* Recreates the original files */
            String name = trueFileList.get(i);
            createCopy("true" + name, name);
            File file = new File(PATH + "true" + name);
            file.delete();
        }
    }
}