# cc-cedict-parser
An InputStream-based Java parser for CC-CEDICT Chinese-English dictionary

Usage example

```java
import com.github.sahlaysta.cccedict.CCCEDICTEntry;
import com.github.sahlaysta.cccedict.CCCEDICTParser;

List<CCCEDICTEntry> dictionary = null;
try {
  dictionary = CCCEDICTParser.parse("C:\\cedict_ts.u8");
} catch (IOException e) {
  e.printStackTrace();
}

int entryIndex = 738;
System.out.println(dictionary.get(entryIndex).simplified);
System.out.println(dictionary.get(entryIndex).traditional);
System.out.println(dictionary.get(entryIndex).pronunciation);
for (String definition: dictionary.get(entryIndex).definitions)
  System.out.println("- " + definition);

/*
* 一网打尽
* 一網打盡
* yi1 wang3 da3 jin4
* - lit. to catch everything in the one net (idiom)
* - fig. to scoop up the whole lot
* - to capture them all in one go
*/

```
