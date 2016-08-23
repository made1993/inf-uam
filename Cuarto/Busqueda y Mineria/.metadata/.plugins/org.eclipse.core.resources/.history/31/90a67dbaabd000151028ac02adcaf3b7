package indexsearch;

import java.io.*;
import java.net.URL;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;

/**
 *
 * @author PC
 */
public class LuceneTest {

    public static void main(String[] args) throws Exception {
        String urls[] = {
            "https://en.wikipedia.org/wiki/Information_theory",
            "https://en.wikipedia.org/wiki/Entropy"
            // Más URLS aquí...
        };
        int topTerms = 20;
        String indexDir = "res/index";
        boolean rebuild = true;
        String query = "entropy";
        int topDocs = 10;

        // Inicio creación del índice
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
        Directory directory = new SimpleFSDirectory(new File(indexDir));
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, analyzer);
        if (rebuild) conf.setOpenMode(OpenMode.CREATE);
        else conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter iwriter = new IndexWriter(directory, conf);

        // Añadir documentos al índice
        for (String url : urls) {
            Document doc = new Document();
            doc.add(new Field("docID", url, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("content", readURLText(url), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
            iwriter.addDocument(doc);
        }
        iwriter.close();

        // Inspeccionar el índice
        IndexReader ireader = IndexReader.open(directory);
        for (int docID = 0; docID < ireader.numDocs(); docID++) {
            TermFreqVector docVector = ireader.getTermFreqVector(docID, "content");
            String terms[] = docVector.getTerms();
            int freqs[] = docVector.getTermFrequencies();
            sort(terms, freqs);
            System.out.println("---------\n" + ireader.document(docID).getFieldable("docID").stringValue() + "\n---------");
            int nTokens = 0;
            for (int i = 0; i < topTerms; i++) 
                System.out.println(terms[i] + "\t" + freqs[i] + "\t" + ireader.docFreq(new Term("content", terms[i])));
            for (int freq : freqs) nTokens += freq;
            System.out.println("\nNr terms: " + terms.length);
            System.out.println("Nr tokens: " + nTokens);
        }

        // Buscar en el índice
        QueryParser parser = new QueryParser(Version.LUCENE_36, "content", analyzer);
        Query q = parser.parse(query);
        IndexSearcher searcher = new IndexSearcher(ireader);
        // Se puede cambiar el modelo IR del buscador con setSimilarity(Similarity)
        ScoreDoc result[] = searcher.search(q, topDocs).scoreDocs;
        System.out.println();
        for (ScoreDoc d : result) 
            System.out.println(d.score + " " + ireader.document(d.doc).getFieldable("docID").stringValue());

        searcher.close();
        ireader.close();
        directory.close();
    }
    
    static void sort(String terms[], int freqs[]) {
        SortedMap<Integer, String> map = new TreeMap<Integer, String>(new Comparator<Integer>() {
            public int compare(Integer n, Integer m) { return m - n; }
        });
        for (int i = 0; i < terms.length; i++) map.put(freqs[i], terms[i]);
        Iterator iter = map.entrySet().iterator();
        for (int i = 0; iter.hasNext(); i++) {
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iter.next();
            terms[i] = entry.getValue();
            freqs[i] = entry.getKey();
        }
    }

    static String readURLText(String url) throws IOException {
        String text = "", line;
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()));
        do {
            line = in.readLine();
            text += line;
        } while (line != null);
        return Jsoup.parse(text).text();
    }
}
