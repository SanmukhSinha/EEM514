import java.io.*;
import java.util.*;

public class processor {

    public static void addheader(String hname,BufferedWriter bwrite,ArrayList<String> headers)throws IOException{
        FileReader read= new FileReader(hname); 
        BufferedReader bread = new BufferedReader(read);
        String str,line;
        boolean com = false;
        if (headers.contains(hname)){
            bread.close();
        } else {
            headers.add(hname);
            while((str = bread.readLine())!=null){
                if(str.indexOf("#")!=-1 && str.indexOf("include")!=-1){
                    String hname1="";
                    int i =  str.indexOf("\"")+1;
                    while(str.charAt(i)!='\"'){
                        hname1 = hname1+str.charAt(i);
                        i++;
                    }
                    addheader(hname1,bwrite,headers);
                }else{
                    line="";
                    str = str.replaceAll("\\s+", " ");
                        if (com){
                            if(str.indexOf("*/")!=-1){
                                com = !com;
                                line = str.substring(str.indexOf("*/")+2);
                            }
                        } else {
                            if(str.indexOf("/*")!=-1){
                                com = !com;
                                line = str.substring(0,str.indexOf("/*"));
                            } else {
                                line = str;
                            }
                        }
                    line = line.trim();
                    if(!line.isEmpty()){
                        bwrite.write(line+'\n');
                    }
                }
            }
        }
        bread.close();
    }

    public static void main(String[] args) throws IOException {
        String name = "tc1.c",temp = "temptc1.c",str;
        BufferedWriter bwrite = new BufferedWriter(new FileWriter(temp));
        ArrayList<String> headers = new ArrayList<String>();
        addheader(name, bwrite, headers);
        bwrite.close();

        BufferedReader bread = new BufferedReader(new FileReader(temp));
        bwrite = new BufferedWriter(new FileWriter("final"+name));
        while((str = bread.readLine())!=null){
            if(str.indexOf("# define")!=-1);
        }


        bread.close();
        bwrite.close();

    }
}