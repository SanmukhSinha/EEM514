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
    create a truecopy of the file
    removeComments
    removeWhitespaces
    scan the file :
        add #includes recursively using writeInclude
        create #define data table
        add #define constants using putDefine Const
*/

public class Preprocessor {
    private static final String PATH = "E:/Coding/Others/Java/EEM514/Assignment2/";

    public static void createCopy(String fileName, String copyFileName) {
        /* makes a copy of file */

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            File oldFile = new File(PATH + fileName);
            File newFile = new File(PATH + copyFileName);

            FileReader fileRead = new FileReader(oldFile);
            FileWriter fileWrite = new FileWriter(newFile);
            reader = new BufferedReader(fileRead);
            writer = new BufferedWriter(fileWrite);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeWhitespaces(String fileName) {
        /* removes all whitespaces, call after removeComments */
        /*Logic: 
            First finds positon of all valid quotes.
            Then removes whitespaces in not quoted text, copies quoted text as it is
        */

        createCopy(fileName, "temp" + fileName);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        File file = new File(PATH + fileName);
        File tempFile = new File(PATH + "temp" + fileName);

        try {
            FileReader fileRead = new FileReader(tempFile);
            FileWriter fileWrite = new FileWriter(file);
            reader = new BufferedReader(fileRead);
            writer = new BufferedWriter(fileWrite);

            String line;
            boolean quote = false;// outside while to include multi-line quotes

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                int pos = 0, i;
                String newLine = "";
                // quotePos stores start and end indices of all qoutes
                ArrayList<Integer> quotePos = new ArrayList<Integer>();

                while (pos < line.length()) {
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
                    pos++;
                }

                if (quotePos.size() > 0) {
                    String tempLine = line.substring(0, quotePos.get(0));
                    newLine = tempLine.replaceAll("\\s+", " ");

                    for (i = 0; i < quotePos.size(); i += 2) {
                        // inside quotes exactly copied
                        newLine = newLine + line.substring(quotePos.get(i), quotePos.get(i + 1) + 1);
                        try {
                            // outside quotes whitespaces removed
                            tempLine = line.substring(quotePos.get(i + 1) + 1, quotePos.get(i + 2));
                            newLine = newLine + tempLine.replaceAll("\\s+", " ");
                        } catch (IndexOutOfBoundsException e) {
                        }
                    }
                    if (quotePos.get(quotePos.size() - 1) < line.length() - 1) {
                        tempLine = line.substring(quotePos.get(quotePos.size() - 1) + 1);
                        newLine = newLine + tempLine.replaceAll("\\s+", " ");
                    }
                } else {
                    // no quotes case
                    newLine = line.replaceAll("\\s+", " ");
                }
                writer.write(newLine + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                tempFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeComments(String fileName) {
        /* removes all c-style comments, call first for a file */
        /*Logic:
            Copies non-comment text using double boolean variable loop.
            Replaces comment by a space.
            Comment can't exist in a Quote and vice-versa.
            Backslash can only be inside a quote.
        */

        createCopy(fileName, "temp" + fileName);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        File file = new File(PATH + fileName);
        File tempFile = new File(PATH + "temp" + fileName);

        try {

            FileReader fileRead = new FileReader(tempFile);
            FileWriter fileWrite = new FileWriter(file);
            reader = new BufferedReader(fileRead);
            writer = new BufferedWriter(fileWrite);

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
                // problem: not sure whether to include this or not
                if (!comment) {
                    // to convert multi-line comments into a single line
                    writer.write('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                tempFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void storeDefine(String line, Hashtable<String, String> defineTableConst,
            Hashtable<String, String> defineTableFunc) {
        /*Stores all #define constants and macros in hashtables*/
        /*Logic:
            Searches for a define, if found then takes the const(or macro)
            name (uses'('to check if const or macro).
            Then takes value till a ' ' for const
            and tales value till endofline for macro.
        */
        int i = line.indexOf("define") + 7;
        char chr;
        String str = "", value = "";

        while ((chr = line.charAt(i)) != ' ') {
            str = str + chr;
            i++;
        }
        i++;

        if (str.indexOf('(') == -1) {
            while (i < line.length() && (chr = line.charAt(i)) != ' ') {
                value = value + chr;
                i++;
            }

            defineTableConst.put(str, value);
        } else {
            if (str.indexOf(')') == -1) {
                while ((chr = line.charAt(i)) != ')') {
                    str = str + chr;
                    i++;
                }
                str = str + chr;
                str = str.replaceAll("\\s+", "");
                i++;
            }

            while (i < line.length()) {
                chr = line.charAt(i);
                value = value + chr;
                i++;
            }
            value = value.replaceAll("\\s+", "");

            defineTableFunc.put(str, value);
        }
    }

    public static String _replaceDefineConst(String line, Hashtable<String, String> defineTableConst) {
        /* works with putDefineConst(), do not use seperately */
        /*Logic:
            Finds for #define const symbols in non-quote area.
            If found, replace it with its value and return the 
            edited line with a space at start.
            This space tells that puDefineConst should be called again
            for this line.
            Thus the same line is called repeatedly until all of its
            consts are replaced with values.
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

    public static String putDefineConst(String line, Hashtable<String, String> defineTableConst) {
        /*Replaces #define constants with their value */
        /*Logic:
            Finds #define constants in the non-quoted area.
            For more info look in _replaceDefine Const().
        */

        boolean quote = false;
        int pos = 0, i;
        String newLine = "", tempLine;
        ArrayList<Integer> quotePos = new ArrayList<Integer>();// quotePos stores start and end indices of all qoutes
        line = line.trim();

        while (pos < line.length()) {
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
            pos++;
        }

        if (quotePos.size() > 0) {

            tempLine = line.substring(0, quotePos.get(0));
            tempLine = _replaceDefineConst(tempLine, defineTableConst);
            if (tempLine.charAt(0) == ' ') {
                return " "+tempLine.substring(1) + line.substring(quotePos.get(0));
            } else {
                newLine = newLine + tempLine;
            }

            for (i = 0; i < quotePos.size(); i += 2) {
                newLine = newLine + line.substring(quotePos.get(i), quotePos.get(i + 1) + 1);
                try {
                    tempLine = line.substring(quotePos.get(i + 1) + 1, quotePos.get(i + 2));
                    tempLine = _replaceDefineConst(tempLine, defineTableConst);
                    if (tempLine.charAt(0) == ' ') {
                        return " "+newLine + tempLine.substring(1) + line.substring(quotePos.get(i + 2));
                    } else {
                        newLine = newLine + tempLine;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }

            if (quotePos.get(quotePos.size() - 1) < line.length() - 1) {
                tempLine = line.substring(quotePos.get(quotePos.size() - 1) + 1);
                tempLine = _replaceDefineConst(tempLine, defineTableConst);
                if (tempLine.charAt(0) == ' ') {
                    return " "+newLine + tempLine.substring(1);
                } else {
                    newLine = newLine + tempLine;
                }
            }
        } else {
            tempLine = line;
            tempLine = _replaceDefineConst(tempLine, defineTableConst);
            if (tempLine.charAt(0) == ' ') {
                return tempLine;
            } else {
                newLine = newLine + tempLine;
            }
        }
        return newLine;
    }

    public static void writeInclude(String str, BufferedWriter writer, ArrayList<String> headerList,
            ArrayList<String> trueFileList) {
        /* To add #include files data */
        /*Logic:
            Looks for headerFile name in quotes.
            If accessed for the first time:
                Adds to trueFileList 
                Creates a trueCopy
                Removes Comments
                Removes Whitespaces
            HeaderList has names of headerfiles already included to prevent recursion
        */
        
        int st = str.indexOf("\"");// finds name of header file enclosed in ""
        int en = str.indexOf("\"", st + 1);
        String headerFile = str.substring(st + 1, en).trim(); // this works for " one.h" but it should not

        if (!(trueFileList.contains(headerFile))) {
            createCopy(headerFile, "true" + headerFile);
            trueFileList.add(headerFile);
            removeComments(headerFile);
            removeWhitespaces(headerFile);
        }

        BufferedReader reader = null;

        if (!headerList.contains(headerFile)) {
            headerList.add(headerFile);
            try {
                FileReader fileRead = new FileReader(PATH + headerFile);
                reader = new BufferedReader(fileRead);

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (!line.isEmpty()) {
                        if (line.charAt(0) == '#' && (line.indexOf("include") == 1 || line.indexOf("include") == 2)) {
                            // problem: works even if there is garbage b/w include and "..."
                            // but may be more reliable
                            writeInclude(line, writer, headerList, trueFileList);
                        } else {
                            writer.write(line + '\n');
                            //System.out.println(line);//
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {

        BufferedReader reader = null;
        BufferedWriter writer = null;
        ArrayList<String> trueFileList = new ArrayList<String>();
        String fileName = "xyz.c";
        // ArrayList<String> headerList = new ArrayList<String>();

        createCopy(fileName, "true" + fileName);
        trueFileList.add(fileName);

        removeComments(fileName);
        removeWhitespaces(fileName);

        createCopy(fileName, "temp" + fileName);
        File tempFile = new File(PATH + "temp" + fileName);
        File mainFile = new File(PATH + fileName);

        /* Deals with #includes */
        try {
            FileReader fileRead = new FileReader(tempFile);
            FileWriter fileWrite = new FileWriter(mainFile);
            reader = new BufferedReader(fileRead);
            writer = new BufferedWriter(fileWrite);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.charAt(0) == '#' && (line.indexOf("include") == 1 || line.indexOf("include") == 2)) {
                        // for including header files
                        ArrayList<String> headerList = new ArrayList<String>();// remove to prevent repeated addition
                        writeInclude(line, writer, headerList, trueFileList);
                    } else {
                        writer.write(line + '\n');
                        //System.out.println(line);//
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                tempFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* Deals with #defines */
        createCopy(fileName, "temp" + fileName);
        try {
            FileReader fileRead = new FileReader(tempFile);
            FileWriter fileWrite = new FileWriter(mainFile);
            reader = new BufferedReader(fileRead);
            writer = new BufferedWriter(fileWrite);

            String line;
            Hashtable<String, String> defineTableConst = new Hashtable<String, String>();
            Hashtable<String, String> defineTableFunc = new Hashtable<String, String>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.charAt(0) == '#' && (line.indexOf("define") == 1 || line.indexOf("define") == 2)) {
                        storeDefine(line, defineTableConst, defineTableFunc);
                    } else {
                        do {
                            System.out.println(line);
                            line = putDefineConst(line, defineTableConst);
                        } while (line.charAt(0) == ' ');
                        writer.write(line + '\n');
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                tempFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        createCopy(fileName, "xyz1.c");

        for (int i = 0; i < trueFileList.size(); i++) {// recreates the original files
            String name = trueFileList.get(i);
            createCopy("true" + name, name);
            File file = new File(PATH + "true" + name);
            file.delete();
        }
    }
}