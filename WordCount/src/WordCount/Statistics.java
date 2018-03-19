package WordCount;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Statistics {

    public  static void ReadC()
    {
        try {
            ArrayList<String> orderSet=new ArrayList<>();//存储指令集
            Scanner scan=new Scanner(System.in);
            String str1=scan.nextLine();
            String strs[]=str1.split(" ");//将指令分隔存入数组
            boolean input1=false;
            for(int i=0;i<strs.length;i++)
            {
                orderSet.add(strs[i]);
            }

            if(orderSet.get(0).equals("wc.exe")) {//判断输入指令是否正确
                //System.out.println("指令格式输入不正确");
                input1=true;
            }
            String encoding="GBK";
            int countChar=0;
            int countword=0;
            int countLine=0;
            int count=0;
            String result="";
            for(int i=0;i<orderSet.size();i++)
            {
                if(orderSet.get(i).equals("-c"))
                    count++;
                if(orderSet.get(i).equals("-w"))
                    count++;
                if(orderSet.get(i).equals("-l"))
                    count++;
            }
            String filePath="./"+orderSet.get(count+1);
            File file=new File(filePath);
            if(input1&&file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt=bufferedReader.readLine())!= null)//read()=-1代表数据读取完毕
                {
                    countChar=countChar+lineTxt.length();//字符个数就是字符长度
                    countword += lineTxt.split(",| ").length;
                    countLine++;
                }

                for(int i=0;i<orderSet.size();i++)
                {
                    if(orderSet.get(i).equals("-c"))
                        result+=(orderSet.get(count+1) + ",字符数：" + countChar+"\n");
                    if(orderSet.get(i).equals("-w"))
                        result+=(orderSet.get(count+1)+",单词数："+countword+"\n");
                    if(orderSet.get(i).equals("-l"))
                        result+=(orderSet.get(count+1)+",行数："+countLine+"\n");
                    if(orderSet.get(i).equals("-o"))
                    {
                        PrintStream out = System.out;// 保存原输出流
                        PrintStream ps=new PrintStream(orderSet.get(orderSet.size()-1));// 创建文件输出流1
                        System.setOut(ps);// 设置使用新的输出流
                    }
                }
                System.out.println(result);
                read.close();
            }else{
                System.out.println("指令格式输入不正确或找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }


    public static void main(String[] args)
    {
        ReadC();

    }
}
