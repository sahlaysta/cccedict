package com.github.sahlaysta.cccedict;

/** Utility class for Pinyin formatting */
public final class CCCEDICTPinyin {
	
	//Invisible constructor
	CCCEDICTPinyin() {}
	
	/** Returns formatted Pinyin of a CC-CEDICT entry */
	public static final String toFormattedPinyin(CCCEDICTEntry entry) {
		String[] syllables = entry.pronunciation.split(" ");
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String syllable: syllables) {
			sb.append(formatPinyinSyllable(syllable));
			if (i < syllables.length - 1)
				sb.append(" ");
			i++;
		}
		return sb.toString();
	}
	
	/** Returns an accent-marked Pinyin syllable String from a CC-CEDICT format pinyin syllable */
	private static final String formatPinyinSyllable(String pinyinSyllable) {
		int tone = getToneOfPinyinSyllable(pinyinSyllable);
		switch (tone) {
		case -1: return pinyinSyllable;
		case 5: {
			if (charSequenceContainsChar(pinyinSyllable, ':')) {
				if (charSequenceContainsChar(pinyinSyllable, 'u')) {
					return removeLastChar(pinyinSyllable.replace("u:", "ü"));
				} else {
					return removeLastChar(pinyinSyllable.replace("U:", "Ü"));
				}
			}
			else {
				return removeLastChar(pinyinSyllable);
			}
		}
		default: {
			if (charSequenceContainsChar(pinyinSyllable, 'a')) {
				return removeLastChar(pinyinSyllable.replace('a', diacriticize('a', tone)));
			}
			else if (charSequenceContainsChar(pinyinSyllable, ':')) {
				if (charSequenceContainsChar(pinyinSyllable, 'u')) {
					return removeLastChar(pinyinSyllable.replace("u:", Character.toString(diacriticize('ü', tone))));
				} else {
					return removeLastChar(pinyinSyllable.replace("U:", Character.toString(diacriticize('Ü', tone))));
				}
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'e')) {
				return removeLastChar(pinyinSyllable.replace('e', diacriticize('e', tone)));
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'o')) {
				return removeLastChar(pinyinSyllable.replace('o', diacriticize('o', tone)));
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'i')) {
				if (charSequenceContainsChar(pinyinSyllable, 'u')) {
					switch (pinyinSyllable.charAt(pinyinSyllable.indexOf('i') - 1)) {
					case 'u': case 'U': return removeLastChar(pinyinSyllable.replace('i', diacriticize('i', tone)));
					default: return removeLastChar(pinyinSyllable.replace('u', diacriticize('u', tone)));
					}
				} else {
					return removeLastChar(pinyinSyllable.replace('i', diacriticize('i', tone)));
				}
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'u')) {
				return removeLastChar(pinyinSyllable.replace('u', diacriticize('u', tone)));
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'A')) {
				return removeLastChar(pinyinSyllable.replace('A', diacriticize('A', tone)));
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'E')) {
				return removeLastChar(pinyinSyllable.replace('E', diacriticize('E', tone)));
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'O')) {
				return removeLastChar(pinyinSyllable.replace('O', diacriticize('O', tone)));
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'I')) {
				if (charSequenceContainsChar(pinyinSyllable, 'U')) {
					switch (pinyinSyllable.charAt(pinyinSyllable.indexOf('I') - 1)) {
					case 'u': case 'U': return removeLastChar(pinyinSyllable.replace('I', diacriticize('I', tone)));
					default: return removeLastChar(pinyinSyllable.replace('U', diacriticize('U', tone)));
					}
				} else {
					return removeLastChar(pinyinSyllable.replace('I', diacriticize('I', tone)));
				}
			}
			else if (charSequenceContainsChar(pinyinSyllable, 'U')) {
				return removeLastChar(pinyinSyllable.replace('U', diacriticize('U', tone)));
			}
			else switch (pinyinSyllable.charAt(0)) {
			case 'r': {
				switch (tone) {
				case 1: return "r\u0304";
				case 2: return "\u0155";
				case 3: return "\u0159";
				default: return "r\u0300";
				}
			}
			case 'R': {
				switch (tone) {
				case 1: return "R\u0304";
				case 2: return "\u0154";
				case 3: return "\u0158";
				default: return "R\u0300";
				}
			}
			case 'm': {
				switch (tone) {
				case 1: return "m\u0304";
				case 2: return "\u1E3f";
				case 3: return "m\u030C";
				default: return "m\u0300";
				}
			}
			default: { //M
				switch (tone) {
				case 1: return "M\u0304";
				case 2: return "\u1E3E";
				case 3: return "M\u030C";
				default: return "M\u0300";
				}
			}
			}
		}
		}
	}
	
	/** Returns the passed String without the last char */
	private static final String removeLastChar(String str) {
		char[] ch = new char[str.length()-1];
		for (int i = 0; i < ch.length; i++)
			ch[i] = str.charAt(i);
		return new String(ch);
	}
	
	/** Returns true if the CharSequence contains the char */
	private static final boolean charSequenceContainsChar(CharSequence cs, char ch) {
		for (int i = 0; i < cs.length(); i++)
			if (cs.charAt(i) == ch)
				return true;
		return false;
	}
	
	/** Diacriticize a Chinese vowel tone */
	private static final char diacriticize(char ch, int tone) {
		switch (ch) {
		case 'a': {
			switch (tone) {
			case 1: return 257;
			case 2: return 'á';
			case 3: return 462;
			default: return 'à';
			}
		}
		case 'e': {
			switch (tone) {
			case 1: return 275;
			case 2: return 'é';
			case 3: return 283;
			default: return 'è';
			}
		}
		case 'i': {
			switch (tone) {
			case 1: return 299;
			case 2: return 'í';
			case 3: return 464;
			default: return 'ì';
			}
		}
		case 'o': {
			switch (tone) {
			case 1: return 333;
			case 2: return 'ó';
			case 3: return 466;
			default: return 'ò';
			}
		}
		case 'u': {
			switch (tone) {
			case 1: return 363;
			case 2: return 'ú';
			case 3: return 468;
			default: return 'ù';
			}
		}
		case 'ü': {
			switch (tone) {
			case 1: return 470;
			case 2: return 472;
			case 3: return 474;
			default: return 476;
			}
		}
		case 'A': {
			switch (tone) {
			case 1: return 256;
			case 2: return 'Á';
			case 3: return 461;
			default: return 'À';
			}
		}
		case 'E': {
			switch (tone) {
			case 1: return 274;
			case 2: return 'É';
			case 3: return 282;
			default: return 'È';
			}
		}
		case 'I': {
			switch (tone) {
			case 1: return 298;
			case 2: return 'Í';
			case 3: return 463;
			default: return 'Ì';
			}
		}
		case 'O': {
			switch (tone) {
			case 1: return 332;
			case 2: return 'Ó';
			case 3: return 465;
			default: return 'Ò';
			}
		}
		case 'U': {
			switch (tone) {
			case 1: return 362;
			case 2: return 'Ú';
			case 3: return 467;
			default: return 'Ù';
			}
		}
		default: { //Ü
			switch (tone) {
			case 1: return 469;
			case 2: return 471;
			case 3: return 473;
			default: return 475;
			}
		}
		}
	}
	
	/** Identify the tone of a singular pinyin syllable. Example: "kuang2" = 2 */
	private static final int getToneOfPinyinSyllable(CharSequence pinyinSyllable) {
		if (isValidPinyinSyllable(pinyinSyllable)) {
			switch (pinyinSyllable.charAt(pinyinSyllable.length() - 1)) {
			case '1': return 1;
			case '2': return 2;
			case '3': return 3;
			case '4': return 4;
			default: return 5;
			}
		}
		else return -1;
	}

	/** Returns true if a singular Pinyin syllable is valid for CC-CEDICT. Example: "kuang3" = true. "ohm7" = false */
	private static boolean isValidPinyinSyllable(CharSequence pinyinSyllable) {
		switch (pinyinSyllable.charAt(pinyinSyllable.length() -1)) {
		case '1': case '2': case '3': case '4': case '5': break;
		default: return false;
		}
		switch (pinyinSyllable.length()) {
		case 2: {
			switch (pinyinSyllable.charAt(0)) {
			case 'a': case 'A': //a
			case 'e': case 'E': //e
			case 'm': case 'M': //m
			case 'o': case 'O': //o
			case 'r': case 'R': //r
				return true;
			default: return false;
			}
		}
		case 3: {
			switch (pinyinSyllable.charAt(0)) {
			case 'a': case 'A': {
				switch (pinyinSyllable.charAt(1)) {
				case 'i': case 'I': //ai
				case 'n': case 'N': //an
				case 'o': case 'O': //o
					return true;
				default: return false;
				}
			}
			case 'b': case 'B': case 'p': case 'P': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': //ba, pa
				case 'i': case 'I': //bi, pi
				case 'o': case 'O': //bo, po
				case 'u': case 'U': //bu, pu
					return true;
				default: return false;
				}
			}
			case 'c': case 'C': case 'd': case 'D': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A':  //ca, da
				case 'e': case 'E':  //ce, de
				case 'i': case 'I':  //ci, di
				case 'u': case 'U':  //cu, du
					return true;
				default: return false;
				}
			}
			case 'e': case 'E': {
				switch (pinyinSyllable.charAt(1)) {
				case 'i': case 'I':  //ei
				case 'n': case 'N':  //en
				case 'r': case 'R':  //er
					return true;
				default: return false;
				}
			}
			case 'f': case 'F': case 'w': case 'W': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A':  //fa, wa
				case 'o': case 'O':  //fo, wo
				case 'u': case 'U':  //fu, wu
					return true;
				default: return false;
				}
			}
			case 'g': case 'G': case 'h': case 'H': case 'k': case 'K': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A':  //ga, ha, ka
				case 'e': case 'E':  //ge, he, ke
				case 'u': case 'U':  //gu, hu, ku
					return true;
				default: return false;
				}
			}
			case 'j': case 'J': case 'q': case 'Q': case 'x': case 'X': {
				switch (pinyinSyllable.charAt(1)) {
				case 'i': case 'I':  //ji, qi, xi
				case 'u': case 'U':  //ju, qu, xu
					return true;
				default: return false;
				}
			}
			case 'l': case 'L': case 'm': case 'M': case 'y': case 'Y': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A':  //la, ma, ya
				case 'e': case 'E':  //le, me, ye
				case 'i': case 'I':  //li, mi, yi
				case 'o': case 'O':  //lo, mo, yo
				case 'u': case 'U':  //lu, mu, yu
					return true;
				default: return false;
				}
			}
			case 'n': case 'N': case 's': case 'S': case 't': case 'T': case 'z': case 'Z': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A':  //na, sa, ta, za
				case 'e': case 'E':  //ne, se, te, ze
				case 'i': case 'I':  //ni, si, ti, zi
				case 'u': case 'U':  //nu, su, tu, zu
					return true;
				default: return false;
				}
			}
			case 'o': case 'O': {
				switch (pinyinSyllable.charAt(1)) {
				case 'u': case 'U':  //ou
					return true;
				default: return false;
				}
			}
			case 'r': case 'R': {
				switch (pinyinSyllable.charAt(1)) {
				case 'e': case 'E':  //re
				case 'i': case 'I':  //ri
				case 'u': case 'U':  //ru
					return true;
				default: return false;
				}
			}
			default: return false;
			}
		}
		case 4: {
			switch (pinyinSyllable.charAt(0)) {
			case 'a': case 'A': case 'e': case 'E': {
				switch (pinyinSyllable.charAt(1)) {
				case 'n': case 'N': {
					switch (pinyinSyllable.charAt(2)) {
					case 'g': case 'G':  //ang, eng
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'b': case 'B': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //bai
					case 'n': case 'N':  //ban
					case 'o': case 'O':  //bao
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //bei
					case 'n': case 'N':  //ben
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A':  //bia
					case 'e': case 'E':  //bie
					case 'n': case 'N':  //bin
					case 'u': case 'U':  //biu
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'c': case 'C': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //cai, cui
					case 'n': case 'N':  //can, cun
					case 'o': case 'O':  //cao, cuo
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N':  //cen
						return true;
					default: return false;
					}
					}
				case 'h': case 'H': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A':  //cha
					case 'e': case 'E':  //che
					case 'i': case 'I':  //chi
					case 'u': case 'U':  //chu
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //cou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'd': case 'D': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //dai, dui
					case 'n': case 'N':  //dan, dun
					case 'o': case 'O':  //dao, duo
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //dei
					case 'n': case 'N':  //den
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A':  //dia
					case 'e': case 'E':  //die
					case 'u': case 'U':  //diu
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //dou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'f': case 'F': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N':  //fan
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //fei
					case 'n': case 'N':  //fen
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //fou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'g': case 'G': case 'h': case 'H': case 'k': case 'K': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //gai, hai, kai
					case 'n': case 'N':  //gan, han, kan
					case 'o': case 'O':  //gao, hao, kao
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //gei, hei, kei
					case 'n': case 'N':  //gen, hen, ken
						return true;
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A':  //gua, hua, kua
					case 'i': case 'I':  //gui, hui, kui
					case 'n': case 'N':  //gun, hun, kun
					case 'o': case 'O':  //guo, huo, kuo
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //gou, hou, kou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'j': case 'J': case 'q': case 'Q': case 'x': case 'X': {
				switch (pinyinSyllable.charAt(1)) {
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'e': case 'E':  //jue, que, xue
					case 'n': case 'N':  //jun, qun, xun
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A':  //jia, qia, xia
					case 'e': case 'E':  //jie, qie, xie
					case 'n': case 'N':  //jin, qin, xin
					case 'u': case 'U':  //jiu, qiu, xiu
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'l': case 'L': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //lai
					case 'n': case 'N':  //lan
					case 'o': case 'O':  //lao
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //lei
						return true;
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case ':':  //lu:
					case 'n': case 'N':  //lun
					case 'o': case 'O':  //luo
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A':  //lia
					case 'e': case 'E':  //lie
					case 'n': case 'N':  //lin
					case 'u': case 'U':  //liu
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //lou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'm': case 'M': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //mai
					case 'n': case 'N':  //man
					case 'o': case 'O':  //mao
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //mei
					case 'n': case 'N':  //men
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'e': case 'E':  //mie
					case 'n': case 'N':  //min
					case 'u': case 'U':  //miu
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //mou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'n': case 'N': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //nai
					case 'n': case 'N':  //nan
					case 'o': case 'O':  //nao
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //nei
					case 'n': case 'N':  //nen
						return true;
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case ':':  //nu:
					case 'n': case 'N':  //nun
					case 'o': case 'O':  //nuo
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'e': case 'E':  //nie
					case 'n': case 'N':  //nin
					case 'u': case 'U':  //niu
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //nou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'p': case 'P': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //pai
					case 'n': case 'N':  //pan
					case 'o': case 'O':  //pao
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //pei
					case 'n': case 'N':  //pen
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'e': case 'E':  //pie
					case 'n': case 'N':  //pin
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //pou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'r': case 'R': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N':  //ran
					case 'o': case 'O':  //rao
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N':  //ren
						return true;
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //rui
					case 'n': case 'N':  //run
					case 'o': case 'O':  //ruo
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //rou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 's': case 'S': case 'z': case 'Z': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //sai, zai, sui, zui
					case 'n': case 'N':  //san, zan, sun, zun
					case 'o': case 'O':  //sao, zao, suo, zuo
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //sei, zei
					case 'n': case 'N':  //sen, zen
						return true;
					default: return false;
					}
					}
				case 'h': case 'H': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A':  //sha, zha
					case 'e': case 'E':  //she, zhe
					case 'i': case 'I':  //shi, zhi
					case 'u': case 'U':  //shu, zhu
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //sou, zou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 't': case 'T': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //tai, tui
					case 'n': case 'N':  //tan, tun
					case 'o': case 'O':  //tao, tuo
						return true;
					default: return false;
					}
					}
				case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //tei
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'e': case 'E':  //tie
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //tou
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'w': case 'W': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'i': case 'I':  //wai, wei
					case 'n': case 'N':  //wan, wen
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'y': case 'Y': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N':  //yan
					case 'o': case 'O':  //yao
						return true;
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'e': case 'E':  //yue
					case 'n': case 'N':  //yun
						return true;
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N':  //yin
						return true;
					default: return false;
					}
					}
				case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U':  //you
						return true;
					default: return false;
					}
					}
				default: return false;
				}
				}
			default: return false;
			}
		}
		case 5: {
			switch (pinyinSyllable.charAt(0)) {
			case 'b': case 'B': case 'm': case 'M': case 'p': case 'P': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //bang, beng, mang, meng, pang, peng
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //bian, mian, pian
						case 'o': case 'O':  //biao, miao, piao
							return true;
						default: return false;
						}
						}
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //bing, ming, ping
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'c': case 'C': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //cang, ceng, cong
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //cuan
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'h': case 'H': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'i': case 'I':  //chai
						case 'n': case 'N':  //chan
						case 'o': case 'O':  //chao
							return true;
						default: return false;
						}
						}
					case 'e': case 'E': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //chen
							return true;
						default: return false;
						}
						}
					case 'u': case 'U': {
					switch (pinyinSyllable.charAt(3)) {
						case 'a': case 'A':  //chua
						case 'i': case 'I':  //chui
						case 'n': case 'N':  //chun
						case 'o': case 'O':  //chuo
							return true;
						default: return false;
						}
						}
					case 'o': case 'O': {
					switch (pinyinSyllable.charAt(3)) {
						case 'u': case 'U':  //chou
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'd': case 'D': case 't': case 'T': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //dang, deng, dong, tang, teng, tong
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //duan, tuan
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //dian, tian
						case 'o': case 'O':  //diao, tiao
							return true;
						default: return false;
						}
						}
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //ding, ting
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'f': case 'F': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //fang, feng
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'o': case 'O':  //fiao
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'g': case 'G': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': case 'i': case 'I': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //gang, geng, ging, gong
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'i': case 'I':  //guai
						case 'n': case 'N':  //guan
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'h': case 'H': case 'k': case 'K': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //hang, heng, hong, kang, keng, kong
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'i': case 'I':  //huai, kuai
						case 'n': case 'N':  //huan, kuan
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'j': case 'J': case 'q': case 'Q': case 'x': case 'X': {
				switch (pinyinSyllable.charAt(1)) {
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //juan, quan, xuan
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //jian, qian, xian
						case 'o': case 'O':  //jiao, qiao, xiao
							return true;
						default: return false;
						}
						}
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //jing, qing, xing
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'l': case 'L': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //lang, leng, long
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //luan
							return true;
						default: return false;
						}
						}
					case ':': {
					switch (pinyinSyllable.charAt(3)) {
						case 'e': case 'E':  //lu:e
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //lian
						case 'o': case 'O':  //liao
							return true;
						default: return false;
						}
						}
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //ling
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'n': case 'N': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //nang, neng, nong
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //nuan
							return true;
						default: return false;
						}
						}
					case ':': {
					switch (pinyinSyllable.charAt(3)) {
						case 'e': case 'E':  //nu:e
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //nian
						case 'o': case 'O':  //niao
							return true;
						default: return false;
						}
						}
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //ning
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'r': case 'R': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //rang, reng, rong
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //ruan
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 's': case 'S': case 'z': case 'Z': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //sang, seng, song, zang, zeng, zong
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //suan, zuan
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'h': case 'H': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'i': case 'I':  //shai, zhai
						case 'n': case 'N':  //shan, zhan
						case 'o': case 'O':  //shao, zhao
							return true;
						default: return false;
						}
						}
					case 'e': case 'E': {
					switch (pinyinSyllable.charAt(3)) {
						case 'i': case 'I':  //shei, zhei
						case 'n': case 'N':  //shen, zhen
							return true;
						default: return false;
						}
						}
					case 'u': case 'U': {
					switch (pinyinSyllable.charAt(3)) {
						case 'a': case 'A':  //shua, zhua
						case 'i': case 'I':  //shui, zhui
						case 'n': case 'N':  //shun, zhun
						case 'o': case 'O':  //shuo, zhuo
							return true;
						default: return false;
						}
						}
					case 'o': case 'O': {
					switch (pinyinSyllable.charAt(3)) {
						case 'u': case 'U':  //shou, zhou
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'w': case 'W': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'e': case 'E': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //wang, weng
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			case 'y': case 'Y': {
				switch (pinyinSyllable.charAt(1)) {
				case 'a': case 'A': case 'i': case 'I': case 'o': case 'O': {
					switch (pinyinSyllable.charAt(2)) {
					case 'n': case 'N': {
					switch (pinyinSyllable.charAt(3)) {
						case 'g': case 'G':  //yang, ying, yong
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
					switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N':  //yuan
							return true;
						default: return false;
						}
						}
					default: return false;
					}
					}
				default: return false;
				}
				}
			default: return false;
			}
		}
		case 6: {
			switch (pinyinSyllable.charAt(0)) {
			case 'b': case 'B': case 'l': case 'L': case 'n': case 'N': {
				switch (pinyinSyllable.charAt(1)) {
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
						switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N': {
							switch (pinyinSyllable.charAt(4)) {
							case 'g': case 'G':  //biang, liang, niang
								return true;
							default: return false;
							}
						}
						default: return false;
						}
					}
					default: return false;
					}
				}
				default: return false;
				}
			}
			case 'c': case 'C': case 'z': case 'Z': {
				switch (pinyinSyllable.charAt(1)) {
				case 'h': case 'H': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': case 'e': case 'E': case 'o': case 'O': {
						switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N': {
							switch (pinyinSyllable.charAt(4)) {
							case 'g': case 'G':  //chang, cheng, chong, zhang, zheng, zhong
								return true;
							default: return false;
							}
						}
						default: return false;
						}
					}
					case 'u': case 'U': {
						switch (pinyinSyllable.charAt(3)) {
						case 'a': case 'A': {
							switch (pinyinSyllable.charAt(4)) {
							case 'i': case 'I':  //chuai, zhuai
							case 'n': case 'N':  //chuan, zhuan
								return true;
							default: return false;
							}
						}
						default: return false;
						}
					}
					default: return false;
					}
				}
				default: return false;
				}
			}
			case 'g': case 'G': case 'h': case 'H': case 'k': case 'K': {
				switch (pinyinSyllable.charAt(1)) {
				case 'u': case 'U': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': {
						switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N': {
							switch (pinyinSyllable.charAt(4)) {
							case 'g': case 'G':  //guang, huang, kuang
								return true;
							default: return false;
							}
						}
						default: return false;
						}
					}
					default: return false;
					}
				}
				default: return false;
				}
			}
			case 'j': case 'J': case 'q': case 'Q': case 'x': case 'X': {
				switch (pinyinSyllable.charAt(1)) {
				case 'i': case 'I': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': case 'o': case 'O': {
						switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N': {
							switch (pinyinSyllable.charAt(4)) {
							case 'g': case 'G':  //jiang, qiang, xiang, jiong, qiong, xiong
								return true;
							default: return false;
							}
						}
						default: return false;
						}
					}
					default: return false;
					}
				}
				default: return false;
				}
			}
			case 's': case 'S': {
				switch (pinyinSyllable.charAt(1)) {
				case 'h': case 'H': {
					switch (pinyinSyllable.charAt(2)) {
					case 'a': case 'A': case 'e': case 'E': {
						switch (pinyinSyllable.charAt(3)) {
						case 'n': case 'N': {
							switch (pinyinSyllable.charAt(4)) {
							case 'g': case 'G':  //shang, sheng
								return true;
							default: return false;
							}
						}
						default: return false;
						}
					}
					case 'u': case 'U': {
						switch (pinyinSyllable.charAt(3)) {
						case 'a': case 'A': {
							switch (pinyinSyllable.charAt(4)) {
							case 'i': case 'I':  //shuai
							case 'n': case 'N':  //shuan
								return true;
							default: return false;
							}
						}
						default: return false;
						}
					}
					default: return false;
					}
				}
				default: return false;
				}
			}
			default: return false;
			}
		}
		case 7: {
			switch (pinyinSyllable.charAt(0)) {
			case 'c': case 'C': case 's': case 'S': case 'z': case 'Z': {
				switch (pinyinSyllable.charAt(1)) {
				case 'h': case 'H': {
					switch (pinyinSyllable.charAt(2)) {
					case 'u': case 'U': {
						switch (pinyinSyllable.charAt(3)) {
						case 'a': case 'A': {
							switch (pinyinSyllable.charAt(4)) {
							case 'n': case 'N': {
								switch (pinyinSyllable.charAt(5)) {
								case 'g': case 'G':  //chuang, shuang, zhuang
									return true;
								default: return false;
								}
							}
							default: return false;
							}
						}
						default: return false;
						}
					}
					default: return false;
					}
				}
				default: return false;
				}
			}
			default: return false;
			}
		}
		default: return false;
		}
	}
}
