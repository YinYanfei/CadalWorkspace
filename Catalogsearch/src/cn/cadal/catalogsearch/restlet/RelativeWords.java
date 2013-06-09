/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.cadal.catalogsearch.restlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chenxing
 */
public class RelativeWords {

    private static String dbUrl = "jdbc:mysql://localhost:3306/webdev_demo?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
    private static String dbUser = "root";
    private static String dbPass = "";
    private static Connection dbConn;

    private String[] words;
    private float[] factors;

    public int length;

    static {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            dbConn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(RelativeWords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RelativeWords(String word) {
        this(word, Integer.MAX_VALUE);
    }

    public RelativeWords(String word, int maxCount) {
        try {
            Statement dbStmt = dbConn.createStatement();
            String sql = String.format(
                    "SELECT q1.text, a.factor " +
                    "FROM AssnSearch_queryword AS q1, AssnSearch_queryword AS q2, AssnSearch_association AS a " +
                    "WHERE q2.text=\"%s\" AND a.assn_from_id = q2.id AND q1.id = a.assn_to_id " +
                    "LIMIT %d", word, maxCount);
            ResultSet result = dbStmt.executeQuery(sql);
            ArrayList<String> wordlist = new ArrayList<String>();
            ArrayList<Float> factorlist = new ArrayList<Float>();
            if (result.first()) {
                do {
                    wordlist.add(result.getString(1));
                    factorlist.add(result.getFloat(2));
                } while (result.next());
            }
            words = new String[wordlist.size()];
            factors = new float[factorlist.size()];
            wordlist.toArray(words);
            Float[] temp = new Float[factors.length];
            factorlist.toArray(temp);
            for (int i = 0; i < temp.length; i++){
                factors[i] = temp[i].floatValue();
            }
            length = words.length;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getWord(int n) {
        return words[n];
    }

    public float getFactor(int n) {
        return factors[n];
    }

    public float[] getFactors() {
        return factors;
    }

    public Hashtable<String, Float> getTermBoost() {
        Hashtable<String, Float> termBoost = new Hashtable<String, Float>();
        for (int i = 0; i < words.length; i++){
            termBoost.put(words[i], new Float(factors[i]));
        }
        
        return termBoost;
    }
}
