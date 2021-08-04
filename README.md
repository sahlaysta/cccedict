# cc-cedict-parser
Java parser for CC-CEDICT Chinese-English dictionary

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
---
Format Pinyin method
```java
import com.github.sahlaysta.cccedict.CCCEDICTPinyin;

int entryIndex = 738;
String pinyin = CCCEDICTPinyin.toFormattedPinyin(dictionary.get(entryIndex));
System.out.println(pinyin);
// yī wǎng dǎ jìn

entryIndex = 3303;
System.out.println(dictionary.get(entryIndex).pronunciation);
System.out.println(CCCEDICTPinyin.toFormattedPinyin(dictionary.get(entryIndex)));
/*
* Zhong1 yang1 Ren2 min2 Zheng4 fu3 Zhu4 Xiang1 gang3 Te4 bie2 Xing2 zheng4 qu1 Lian2 luo4 Ban4 gong1 shi4
* Zhōng yāng Rén mín Zhèng fǔ Zhù Xiāng gǎng Tè bié Xíng zhèng qū Lián luò Bàn gōng shì
*/
```
