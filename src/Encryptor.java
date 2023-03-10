import java.util.Arrays;

public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        int letter = 0;
        for (int row = 0; row < numRows; row++){
            for (int col = 0; col < numCols; col++){
                if (letter < str.length()){
                    letterBlock[row][col] = str.charAt(letter) + "";
                    letter++;
                }
                else {
                    letterBlock[row][col] = "A";
                }

            }
        }
    }
    public void fillBlockCol(String str)
    {
        int letter = 0;
            for (int col = 0; col < numCols; col++){
                for (int row = 0; row < numRows; row++){
                    if (letter < str.length()){
                    letterBlock[row][col] = str.charAt(letter) + "";
                    letter++;
                }
                    else {
                    letterBlock[row][col] = "A";
                }

            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String returnString = "";
        for (int col = 0; col < numCols; col++){
            for (int row = 0; row < numRows; row++){
                returnString+= letterBlock[row][col];
            }
        }
        return returnString;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message) {
        String finalMessage = "";
        int startIndex = 0;
        int key = numCols * numRows;
        for (int i = 0; i < message.length() / key; i++){
            fillBlock(message.substring(startIndex, startIndex+key));
            startIndex+= key;
            finalMessage += encryptBlock();
        }
        if (message.length()%key > 0) {
            fillBlock(message.substring(startIndex, startIndex + (message.length() % key)));
            finalMessage += encryptBlock();
        }
        return finalMessage;
    }
    public String blockToString(String[][] arr){
        String returnString = "";
        for (int row = 0; row < arr.length; row++){
            for (int col = 0; col < arr[0].length; col++){
                returnString+= arr[row][col];
            }
        }
        return returnString;
    }


    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        String finalMessage = "";
        int startIndex = 0;
        int key = numCols * numRows;
        for (int i = 0; i < encryptedMessage.length()/key; i++){
            fillBlockCol(encryptedMessage.substring(startIndex, startIndex + key));
            startIndex+= key;
            finalMessage += blockToString(letterBlock);
        }
        if (encryptedMessage.length() % key > 0){
            fillBlock(encryptedMessage.substring(startIndex, startIndex + (encryptedMessage.length() % key)));
            finalMessage += blockToString(letterBlock);
        }
        String checkString = "A";
        int c = finalMessage.length()-1;
        while (checkString.equals("A")){
            checkString = finalMessage.charAt(c) + "";
            if (checkString.equals("A")){
                finalMessage = finalMessage.substring(0, c);
            }
            c--;
        }
        return finalMessage;
    }
    public String superEncryptMessage(String message, int shift){
        String messageShifted = "";
        for (char c : message.toCharArray()){
            messageShifted+= (char)(c + shift);
        }
        return encryptMessage(messageShifted);
    }
    public String superDecryptMessage(String message, int shift){
        String origMessage = "";
        for (char c : decryptMessage(message).toCharArray()){
            origMessage += (char)(c - shift);
        }
        return origMessage;
    }
}