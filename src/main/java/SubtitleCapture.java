
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SubtitleCapture {

    public static void main(String[] args) {

        String paragraph = "Vamos ver o que vai acontecer por aqui. Teste, escrevedo mais coisas.";

        //Loading sentence detector model
        InputStream inputStream = null;


        try {
            inputStream = new FileInputStream("D:\\Git\\plnLegendas\\models\\pt-sent.bin");
            SentenceModel model = null;
            model = new SentenceModel(inputStream);
            //Instantiating the SentenceDetectorME class
            SentenceDetectorME detector = new SentenceDetectorME(model);

            //Detecting the position of the sentences in the raw text
            Span spans[] = detector.sentPosDetect(paragraph);

            //Printing the spans of the sentences in the paragraph
            for (Span span : spans) {
                System.out.println(span);
            }
            /**
             * [0..39)
             * [40..69)
             */

            String[] sequences = detector.sentDetect(paragraph);

            inputStream = new FileInputStream("D:\\Git\\plnLegendas\\models\\pt-pos-maxent.bin");

            POSModel modelPOS = null;
            modelPOS = new POSModel(inputStream);

            POSTaggerME tagger = new POSTaggerME(modelPOS);

            //Instantiating SimpleTokenizer class
            SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;

            for(String s : sequences) {
                //Tokenizing the given sentence
                String tokens[] = simpleTokenizer.tokenize(s);

                String[] tags = tagger.tag(tokens);
                for(int i=0;i<tokens.length;i++){
                    System.out.println(tokens[i] + " - " + tags[i]);
                }
                /**
                 * Vamos - v-fin
                 * ver - v-inf
                 * o - punc
                 * que - conj-s
                 * vai - v-fin
                 * acontecer - v-inf
                 * por - prp
                 * aqui - adv
                 * . - punc
                 * Teste - n
                 * , - punc
                 * escrevedo - v-pcp
                 * mais - adv
                 * coisas - n
                 * . - punc
                 */
            }

            inputStream = new FileInputStream("D:\\Git\\plnLegendas\\models\\pt-token.bin");
            TokenizerModel modelToken = new TokenizerModel(inputStream);
            TokenizerME tokenizer = new TokenizerME(modelToken);
            String tokens[] = tokenizer.tokenize(paragraph);
            double tokenProbs[] = tokenizer.getTokenProbabilities();
            System.out.println("Token\t: Probability\n-------------------------------");
            for(int i=0;i<tokens.length;i++){
                System.out.println(tokens[i]+"\t: "+tokenProbs[i]);
            }

            /**
             * Token	: Probability
             * -------------------------------
             * Vamos	: 1.0
             * ver	: 1.0
             * o	: 1.0
             * que	: 1.0
             * vai	: 1.0
             * acontecer	: 1.0
             * por	: 1.0
             * aqui	: 0.9906174615307396
             * .	: 1.0
             * Teste	: 0.9997552628120784
             * ,	: 1.0
             * escrevedo	: 1.0
             * mais	: 1.0
             * coisas	: 0.9977103932399807
             * .	: 1.0
             */

            inputStream = new FileInputStream("D:\\Git\\plnLegendas\\models\\pt-pos-perceptron.bin");

            modelPOS = null;
            modelPOS = new POSModel(inputStream);

            tagger = new POSTaggerME(modelPOS);

            //Instantiating SimpleTokenizer class
            simpleTokenizer = SimpleTokenizer.INSTANCE;

            for(String s : sequences) {
                //Tokenizing the given sentence
                String tokens2[] = simpleTokenizer.tokenize(s);

                String[] tags = tagger.tag(tokens2);
                for(int i=0;i<tokens2.length;i++){
                    System.out.println(tokens2[i] + " - " + tags[i]);
                }
                /**
                 * Vamos - v-fin
                 * ver - v-inf
                 * o - pron-det
                 * que - pron-indp
                 * vai - v-fin
                 * acontecer - v-inf
                 * por - prp
                 * aqui - adv
                 * . - punc
                 * Teste - n
                 * , - punc
                 * escrevedo - v-pcp
                 * mais - adv
                 * coisas - n
                 * . - punc
                 */
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
