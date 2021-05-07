package article;

import java.util.Random;

public class A1 {
    public static void main(String[] args) {
        for (int i = 0; i < 22; i++) {
            System.out.println(produceSentence());
        }
    }

    public static String produceSentence() {
        Random rand = new Random();
        int randNum = rand.nextInt(SENTENCE_MODEL.length);
        String model = SENTENCE_MODEL[randNum];
        String result = "";
        result =
                model.replaceFirst("xxxx", FOUR_CHARS_WORDS[rand.nextInt(FOUR_CHARS_WORDS.length)]);
        result =
                result.replaceFirst("xxxx", FOUR_CHARS_WORDS[rand.nextInt(FOUR_CHARS_WORDS.length)]);
        result =
                result.replaceFirst("xxxx", FOUR_CHARS_WORDS[rand.nextInt(FOUR_CHARS_WORDS.length)]);
        result =
                result.replaceFirst("xx", TWO_CHARS_WORDS[rand.nextInt(TWO_CHARS_WORDS.length)]);
        result =
                result.replaceFirst("xx", TWO_CHARS_WORDS[rand.nextInt(TWO_CHARS_WORDS.length)]);
        result =
                result.replaceFirst("x", TWO_CHARS_WORDS[rand.nextInt(TWO_CHARS_WORDS.length)].charAt(rand.nextInt(2))+"");
        result =
                result.replaceFirst("x", TWO_CHARS_WORDS[rand.nextInt(TWO_CHARS_WORDS.length)].charAt(rand.nextInt(2))+"");
        result =
                result.replaceFirst("x", TWO_CHARS_WORDS[rand.nextInt(TWO_CHARS_WORDS.length)].charAt(rand.nextInt(2))+"");
        result =
                result.replaceFirst("x", TWO_CHARS_WORDS[rand.nextInt(TWO_CHARS_WORDS.length)].charAt(rand.nextInt(2))+"");
        return result;

    }


    public static final String [] SENTENCE_MODEL = {
            "xxxx，xxxx，不过是一场xxxx。","你说xxxx，我说xxxx，最后不过xxxx。",
            "xx，xx，许我一场xxxx。 ","你说xxxxxxxx，后来xxxxxxxx。",
            "一x一x一xx，半x半x半xx。","xxxx，xxxx，终不敌xxxx。"};


    public static final String [] TWO_CHARS_WORDS =
            {"朱砂", "天下", "杀伐", "人家","韶华","风华","繁华", "血染", "墨染", "白衣",
                    "素衣","嫁衣","倾城", "孤城","空城","旧城","旧人","伊人","春风","心醉","古琴",
                    "无情","迷离","奈何","断弦","焚尽","散乱","陌路","乱世","笑靥","浅笑","明眸" ,
                    "轻叹","烟火","一生","三生","浮生","桃花","梨花","落花","烟花","离殇 ","情殇",
                    "爱殇", "剑殇","灼伤","仓皇","匆忙","陌上","清商","焚香","墨香","微凉","断肠",
                    "痴狂","凄凉","黄梁","未央","成双","无恙","虚妄"," 凝霜","洛阳 ","长安","江南",
                    "忘川","千年","纸伞","烟雨","回眸","公子 ","红尘 ","红颜","红衣","红豆","红线 ",
                    "青丝","青史","青冢","白发","白首","白骨","黄土","黄泉","碧落","紫陌"};

    public static final String [] FOUR_CHARS_WORDS = {
            "情深缘浅","情深不寿","莫失莫忘","阴阳相隔","如花美眷","似水流年 ","眉目如画",
            "曲终人散","繁华落尽","不诉离殇","一世长安"};

}
