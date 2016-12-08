package sample;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class controller{
    FileInputStream in = null;
    ArrayList<String> sentences;
    String str;
    int sentence_length=0;
    int word_length=0;
    ArrayList<Integer> found_list;
    ArrayList<String> tokens;
    ArrayList<String> stemmed_words;
    ArrayList<String>stopwords;
    String content=null;
    ArrayList<String> stems1,stems2,stems3,stems4,stems5;
    ArrayList<ArrayList<String>> stems_list;

    controller(String file_loc) {

        sentences=new ArrayList<String>();
        stopwords=new ArrayList<>();
        found_list=new ArrayList<Integer>();
        stemmed_words=new ArrayList<String>();
        tokens=new ArrayList<String>();
        stems_list=new ArrayList<>();
        stems1=new ArrayList<>();
        stems2=new ArrayList<>();
        stems3=new ArrayList<>();
        stems4=new ArrayList<>();
        stems5=new ArrayList<>();
        stems_list.add(stems1);stems_list.add(stems2);stems_list.add(stems3);stems_list.add(stems4);stems_list.add(stems5);

        int i=0;
        try {

            File fileDir = new File(file_loc);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            while ((str = in.readLine()) != null) {
                content=str;
            }
            in.close();

            File dir = new File("C:\\Users\\bihani ayush\\Desktop\\stems\\");

           if(dir.isDirectory()) {

                for(String f : dir.list()) {
                    f="C:\\Users\\bihani ayush\\Desktop\\stems\\".concat(f);
                    File file=new File(f);

                    if(file.isFile()) {

                        BufferedReader stem_input= new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(file), "UTF8"));
                        while ((str = stem_input.readLine()) != null) {

                            stems_list.get(i).add(str);

                        }

                    }
                    i++;

                }
            } else {
                System.out.println("Invalid Directory");

            }

            remove_hyphens();


        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
    public void generate_sentence(){

        String[] arr=content.split("\u0964");

        for ( String ss : arr) {
            sentence_length++;
            sentences.add(ss);
            System.out.println(ss);
        }


    }
    public ArrayList<String> get_stopwords(){

        return stopwords;
    }
    public void remove_stopwords() {


        try {

            File fileDir = new File("C:\\Users\\bihani ayush\\Desktop\\stop_words.txt");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));


            while ((str = in.readLine()) != null) {
                stopwords.add(str);

            }
            for(String word:tokens){
                if(stopwords.contains(word))
                {
                    stemmed_words.remove(word);
                    System.out.println(word);
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void remove_hyphens(){

        content=content.replace("-"," ");
    }

    public void generate_tokens(){
        if(sentences.isEmpty()) {
            String[] sentence = content.split("\u0964");

            for (String ss : sentence) {
                sentence_length++;
                sentences.add(ss);
            }
        }
        for(String sentence_words:sentences ){
        for ( String ss : sentence_words.split(" ")) {
            word_length++;
            tokens.add(ss);
            stemmed_words.add(ss);
            found_list.add(0);


        }}

    }
    public int sentence_length(){

        return sentence_length;
    }
    public int words_length(){

        return word_length;
    }

    public void generate_stemwords(){



//        HashMap<Integer,ArrayList<String>> stemwords=new HashMap<>();
//        stemwords.put(1,stems1);
//        stemwords.put(2,stems2);
//        stemwords.put(3,stems3);
//        stemwords.put(4,stems4);
//        stemwords.put(5,stems5);
//        ArrayList<String> stems;
//
//        for(String token:tokens){
//            Iterator it=stemwords.entrySet().iterator();
//            while(it.hasNext()){
//                Map.Entry pair=(Map.Entry)it.next();
//                stems=(ArrayList)pair.getValue();
//                int l=(int)pair.getKey();
//               for(String stem:stems){
//                   char[] token_array=token.toCharArray();
//
//               }
//            }
//        }
        ArrayList<String> temp=new ArrayList<String>();
        ArrayList<String> nouns=new ArrayList<String>();
        int word_count=0;
        String exp="(.*)";
        try {
            File fileDir = new File("C:\\Users\\bihani ayush\\Desktop\\hin.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            while ((str = in.readLine()) != null) {
                nouns.add(str);
            }

        }
            catch(Exception e){}

            for(String word :tokens){
                if(nouns.contains(word)) {
                   System.out.println( word + "\t is already in root form.");
                    stemmed_words.remove(word);
                    word_count++;
                }
//
//
//
                else{
                    if(found_list.get(word_count)==0){

                        for(String reg:stems_list.get(0))
                        {
                            String regex=exp.concat(reg);
                            Pattern p=Pattern.compile(regex);
                            Matcher m=p.matcher(word);

                            int length=reg.length();

                            if(m.find()){

                                    if(nouns.contains(word.substring(0, m.end() - length))) {
                                       System.out.println("Rule 3: \t"+word + " \t:" + word.substring(0, m.end() - length));
                                       found_list.set(found_list.get(word_count),1);
                                    }
                                }
                            }

                        }

                        if(found_list.get(word_count)==0){

                            for(String reg:stems_list.get(2)){
                                String regex=exp.concat(reg);
                                Pattern p=Pattern.compile(regex);
                                Matcher m=p.matcher(word);
                                int length=reg.length();
                                if(m.find()){
                                    String stem_trim=word.substring(0,m.end()-length);
                                    String unicode="\u093E";
                                    String trimmed=stem_trim.concat(unicode);
                                    if(nouns.contains(trimmed)) {
                                        System.out.println("Rule 4: \t" +word + " \t:" + trimmed +1 );
                                        found_list.set(found_list.get(word_count),1);
                                    }
                                }
                            }
                        }

                    if(found_list.get(word_count)==0){
                        for(String reg:stems_list.get(1)){

//                            String newreg;
//                            char[]arr= reg.toCharArray();
//                            StringBuilder builder=new StringBuilder();
//                                if(word.length()>reg.length()+1){
//                                for (int i = 1; i < arr.length; i++) {
//                                    builder.append(arr[i]);
//                                }
//                                newreg = builder.toString();
                                  int length=reg.length();
                                String regex = exp.concat(reg);
                                Pattern p = Pattern.compile(regex);
                                Matcher m = p.matcher(word);
                                if(m.find()){
                                    String stem_trim=word.substring(0,m.end()-length);
                                    char penultimate_char=stem_trim.charAt(stem_trim.length()-1);
                                    String new_stem_trim=word.substring(0,m.end()-length-1);
                                    String unicode="\u0940";
                                    String trimmed=new_stem_trim.concat(Character.toString(penultimate_char)).concat(unicode);

                                    System.out.println("Rule 5: \t"+word +" \t :"+trimmed);
                                    if(nouns.contains(trimmed)) {
                                        //System.out.println(word + "\t:" + trimmed);
                                        found_list.set(found_list.get(word_count), 1);
                                    }
                                }}
                        }

                    if(found_list.get(word_count)==0)
                    {
                        String suffix="ए";
                            String regex=exp.concat(suffix);
                            Pattern p=Pattern.compile(regex);
                            Matcher m=p.matcher(word);
                            int length=1;
                            if(m.find()){
                                String stem_trim=word.substring(0,m.end()-length);

                                String unicode="\u0906";
                               // System.out.println(stem_trim+found_list.get(word_count));
                                String trimmed=stem_trim.concat(unicode);

                               //System.out.println(trimmed);
                                if(nouns.contains(trimmed)) {
                                    System.out.println("Rule 6: \t"+word + " \t:" + trimmed );
                                    found_list.set(found_list.get(word_count),1);
                                }
                            }
                    }

                    word_count++;
                    }

              }
        }
    public static void main(String[] args){

       controller tok=new controller("C:\\Users\\bihani ayush\\Desktop\\hindi_txt.txt");
        tok.generate_tokens();
        tok.generate_stemwords();
        tok.generate_sentence();
//        String kitbab="किताब";
//        char[] arr=kitbab.toCharArray();
//        for(int i=0;i<arr.length;i++){
//            System.out.println(Integer.toHexString((int)arr[i]));
//        }
    System.out.println("Input given above.");

    }
}

