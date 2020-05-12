package validators;

public class Validators {
    //return 1 in case of success
    public static int validateEmail(String email)
    {
        if(email.length()<3)
        {
            return 0;
        }
        else{
            int contor=0;
            for(int i=0; i<email.length(); i++)
            {
                if(email.charAt(0)=='@'  || email.charAt(email.length()-1)=='@')
                {
                    return 0;
                }

                if(email.charAt(i)=='@')
                {
                    contor++;
                }

            }
            if(contor==0)
            {
                return 0;
            }
            else if(contor>1)
            {
                return 0;
            }
        }
        return 1;
    }

    //return 1 in case of success
    public static int validateName(String text)
    {
        char ch;
        if(text.length()<3)
            return 0;
        for(int i=0; i<text.length(); i++)
        {
            ch=text.charAt(i);
            int ok=0;
            if(!(ch>='a' && ch<='z')){
                ok++; //1
            }
            if(!(ch>='A' && ch<='Z')){
                ok++; //2
            }
            if(ch != ' ')
                ok++;
            if(ok==3){
                return 0;
            }
        }
        return 1;
    }

    //return 1 in case of success
    public static int validatePass(String text)
    {
        if(text.length()<3){
            return 0;
        }
        if(text.length()>16){
            return 0;
        }
        return 1;
    }

    //return 1 in case of success
    public static int validateNumberBox(String text)
    {
        char ch;
        if(text.length()==0)
            return 0;
        if(text.length()>4)
            return 0;

        for(int i=0; i<text.length(); i++){
            ch=text.charAt(i);
            if(!(ch>='0' && ch<='9'))
            {
                return 0;
            }
        }
        return 1;
    }

    //return 1 in case of success
    public static int validateDoubleBox(String text)
    {
        char ch;
        if(text.length()==0)
            return 0;
        if(text.length()>5)
            return 0;

        for(int i=0; i<text.length(); i++){
            ch=text.charAt(i);
            if(!(ch>='0' && ch<='9') && ch!='.')
            {
                return 0;
            }
        }
        return 1;
    }

}
