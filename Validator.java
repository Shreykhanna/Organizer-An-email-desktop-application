package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Validator {
    public static void main(String args[]){
        Validator valid=new Validator();
        String str="siddharth";
        boolean b=valid.hasNoNumber(str);
        System.out.println(str+" contains "+((b)? "no numbers": "numbers"));
        b=valid.onlyAlphabets(str);
        System.out.println(str+" contains "+((b)? "only alphabets": " non alphabets characters"));
    }
    
    public boolean hasNoNumber(String content){
        boolean result=false;
        String regex=".*\\d.*";
        Pattern pattern=Pattern.compile(regex);
        Matcher match=pattern.matcher(content);
        result=!match.find();
        return result;
    }
    
    public boolean onlyAlphabets(String content){
        boolean result=false;
        String regex="[a-z][A-Z]+";
        Pattern pattern=Pattern.compile(regex);
        Matcher match=pattern.matcher(content);
        System.out.println(match.find());
        result=!match.find();
        return result;
    }
    
    public boolean isEmpty(Object field){
        boolean result=false;
        if (field instanceof JPasswordField){
            JPasswordField pwdField=(JPasswordField)field;
            int size=pwdField.getPassword().length;
            if(size < 1){
                result=true;
            }
        }else if(field instanceof JTextField){
            JTextField textField=(JTextField)field;
            int size=textField.getText().length();
            if(size < 1){
                result=true;
            }
        }
        return result;
    }
    
    public boolean checkLength(Object field){
        boolean result=true;
        if (field instanceof JPasswordField){
            JPasswordField pwdField=(JPasswordField)field;
            if(pwdField.getPassword().length < 8){
                result=false;
            }
        }
        return result;
    }
    
    
}
