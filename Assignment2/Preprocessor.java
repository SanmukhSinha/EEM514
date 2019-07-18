import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
            boolean quote = false;

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
        /* removes all c-style comments, call first foa a file */
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

    public static void writeInclude(String str, BufferedWriter writer, ArrayList<String> headerList,
            ArrayList<String> trueFileList) {
        /* To add #include files data */
        // headerList has names of headerfiles already included to prevent recursion

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
                        if (line.charAt(0) == '#') {
                            // problem: works even if there is garbage b/w include and "..."
                            // but may be more reliable
                            if (line.indexOf("include") == 1 || line.indexOf("include") == 2) {
                                // to include header file
                                writeInclude(line, writer, headerList, trueFileList);
                            }
                        } else {
                            writer.write(line + '\n');
                            System.out.println(line);//
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

        try {
            FileReader fileRead = new FileReader(PATH + "xyz.c");
            FileWriter fileWrite = new FileWriter(PATH + "tempxyz.c");
            reader = new BufferedReader(fileRead);
            writer = new BufferedWriter(fileWrite);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {

                    if (line.charAt(0) == '#') {
                        // for including header files
                        if (line.indexOf("include") == 1 || line.indexOf("include") == 2) {
                            ArrayList<String> headerList = new ArrayList<String>();// remove from here to prevent
                                                                                   // repeated addition
                            writeInclude(line, writer, headerList, trueFileList);
                        }
                    } else {
                        writer.write(line + '\n');
                        System.out.println(line);//
                    }
                }
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

        for (int i = 0; i < trueFileList.size(); i++) {// recreates the original files
            String name = trueFileList.get(i);
            createCopy("true" + name, name);
            File file = new File(PATH + "true" + name);
            file.delete();
        }
    }
}