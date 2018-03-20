package WordCount;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Statistics {


    public ArrayList<File> wjList;
    public void ReadOrder()
    {
        try {
            wjList = new ArrayList<>();
            ArrayList<String> orderSet=new ArrayList<>();//存储指令集
            ArrayList<String> stopSet=new ArrayList<>();//存储停用词
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
            int countChar=0;//字符数
            int countword=0;//单词数
            int countLine=0;//行数
            int codeLine=0;//代码行
            int noneLine=0;//空行
            int noteLine=0;//注释行
            int count=0;//用来寻找文件在指令中的位置
            boolean isAll=false;//用来判断是否有“-s”命令
            boolean isStop=false;//用来判断是否有“-e”命令
            boolean isOut=false;//用来判断是否有“-o”命令
            String stopath="";
            String result="";
            for(int i=0;i<orderSet.size();i++)
            {
                if(orderSet.get(i).equals("-c"))
                    count++;
                if(orderSet.get(i).equals("-w"))
                    count++;
                if(orderSet.get(i).equals("-l"))
                    count++;
                if(orderSet.get(i).equals("-s"))
                {
                    isAll=true;
                    count++;
                }
                if(orderSet.get(i).equals("-a"))
                    count++;
                if(orderSet.get(i).equals("-e"))
                {
                    isStop=true;
                    stopath=orderSet.get(i+1);
                    count+=2;
                }
                if(orderSet.get(i).equals("-o"))
                    isOut=true;
            }
            if(isStop)//存在“-e”命令
            {
                File file=new File(stopath);
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt=bufferedReader.readLine())!= null)//read()=-1代表数据读取完毕
                {
                    String stops[]=lineTxt.split(" ");
                    for(int i=0;i<stops.length;i++)
                    {
                        stopSet.add(stops[i]);
                    }
                }
            }
            if(!isAll)
            {
                String filePath=orderSet.get(count+1);
                File file=new File(filePath);
                if(input1&&file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt=bufferedReader.readLine())!= null)//read()=-1代表数据读取完毕
                    {
                        countChar=countChar+lineTxt.length();//字符个数就是字符长度
                        countword += lineTxt.split(",| ").length;
                        String lineWords[]=lineTxt.split(",| ");
                        countLine++;
                        if(lineTxt.length()<=1)
                            noneLine++;
                        else if((lineTxt.indexOf("//")!=-1)||(lineTxt.indexOf("/*")!=-1)||(lineTxt.indexOf("*/")!=-1))
                            noteLine++;
                        else
                            codeLine++;
                        if(isStop)
                        {
                            for(int i=0;i<stopSet.size();i++)
                            {
                                for(int m=0;m<lineWords.length;m++) {
                                    if (stopSet.get(i).equals(lineWords[m]))
                                        countword--;
                                }
                            }
                        }
                    }
                    for(int i=0;i<orderSet.size();i++)
                    {
                        if(orderSet.get(i).equals("-c"))
                            result+=(orderSet.get(count+1) + ",字符数：" + countChar+"\r\n");
                        if(orderSet.get(i).equals("-w"))
                            result+=(orderSet.get(count+1)+",单词数："+countword+"\r\n");
                        if(orderSet.get(i).equals("-l"))
                            result+=(orderSet.get(count+1)+",行数："+countLine+"\r\n");
                        if(orderSet.get(i).equals("-a"))
                            result+=(orderSet.get(count+1)+",代码行/空行/注释行："+codeLine+"/"+noneLine+"/"+noteLine+"\r\n");

                    }
                    if(isOut)
                    {
                        PrintStream out = System.out;// 保存原输出流
                        System.out.println("指令正确，结果已存入相应文件");
                        PrintStream ps=new PrintStream(orderSet.get(orderSet.size()-1));// 创建文件输出流1
                        System.setOut(ps);// 设置使用新的输出流
                    }
                    else
                    {
                        System.out.println("指令正确，结果已存入相应文件");
                        PrintStream ps1=new PrintStream("result.txt");// 创建文件输出流2
                        System.setOut(ps1);// 设置使用新的输出流
                    }
                    System.out.println(result);
                    Scanner scan1=new Scanner(System.in);
                    String str=scan.nextLine();
                    read.close();
                }
                else{
                    System.out.println("指令格式输入不正确或找不到指定的文件");
                }
            }
            else//指令中存在“-s”
            {
                traverseFolder2("./");
                for(int j=0;j<wjList.size();j++)
                {
                    int countChar1=0;//字符数
                    int countword1=0;//单词数
                    int countLine1=0;//行数
                    int codeLine1=0;//代码行
                    int noneLine1=0;//空行
                    int noteLine1=0;//注释行
                    File file=new File(wjList.get(j).getPath());
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt=bufferedReader.readLine())!= null)//read()=-1代表数据读取完毕
                    {
                        countChar1=countChar1+lineTxt.length();//字符个数就是字符长度
                        countword1 += lineTxt.split(",| ").length;
                        String lineWords[]=lineTxt.split(",| ");
                        countLine1++;
                        if(lineTxt.length()<=1)
                            noneLine1++;
                        else if((lineTxt.indexOf("//")!=-1)||(lineTxt.indexOf("/*")!=-1)||(lineTxt.indexOf("*/")!=-1))
                            noteLine1++;
                        else
                            codeLine1++;
                        if(isStop)
                        {
                            for(int i=0;i<stopSet.size();i++)
                            {
                                for(int m=0;m<lineWords.length;j++) {
                                    if (stopSet.get(i).equals(lineWords[m]))
                                        countword--;
                                }
                            }
                        }
                    }

                    for(int i=0;i<orderSet.size();i++)
                    {
                        if(orderSet.get(i).equals("-c"))
                            result+=(wjList.get(j).getName() + ",字符数：" + countChar1+"\r\n");
                        if(orderSet.get(i).equals("-w"))
                            result+=(wjList.get(j).getName()+",单词数："+countword1+"\r\n");
                        if(orderSet.get(i).equals("-l"))
                            result+=(wjList.get(j).getName()+",行数："+countLine1+"\r\n");
                        if(orderSet.get(i).equals("-a"))
                            result+=(wjList.get(j).getName()+",代码行/空行/注释行："+codeLine1+"/"+noneLine1+"/"+noteLine1+"\r\n");
                    }
                    if(isOut)
                    {
                        PrintStream out = System.out;// 保存原输出流
                        System.out.println("指令正确，结果已存入相应文件");
                        PrintStream ps=new PrintStream(orderSet.get(orderSet.size()-1));// 创建文件输出流1
                        System.setOut(ps);// 设置使用新的输出流
                    }
                    else
                    {
                        System.out.println("指令正确，结果已存入相应文件");
                        PrintStream ps1=new PrintStream("result.txt");// 创建文件输出流2
                        System.setOut(ps1);// 设置使用新的输出流
                    }
                    read.close();
                }
                System.out.println(result);
                Scanner scan1=new Scanner(System.in);
                String str=scan.nextLine();

            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public void traverseFolder2(String path)//递归处理文件
    {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    traverseFolder2(file2.getAbsolutePath());
                } else {
                    if(file2.getName().endsWith(".c")) {
                        wjList.add(file2);//存储符合条件的文件
                    }
                }
                }
            }

    }



}
