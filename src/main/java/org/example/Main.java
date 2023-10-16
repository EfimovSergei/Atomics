package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger three = new AtomicInteger(0);
    public static AtomicInteger four = new AtomicInteger(0);
    public static AtomicInteger five = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        List<Thread> threads = new ArrayList<>(3);

        Thread thread1 = new Thread(() -> {
            for (String str : texts) {
                if (istPalindrom(str)) {
                    choiceAtomic(str.length());
                }
            }
        });
        threads.add(thread1);
        Thread thread2 = new Thread(() -> {
            for (String str : texts) {
                if (isSameCharacters(str)) {
                    choiceAtomic(str.length());
                }
            }
        });
        threads.add(thread2);
        Thread thread3 = new Thread(() -> {
            for (String str : texts) {
                if (isRaise(str)) {
                    choiceAtomic(str.length());
                }
            }
        });
        threads.add(thread3);
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("\"Красивых\" слов (3 буквы): " + three.get() + "\n" +
                "\"Красивых\" слов (4 буквы): " + four.get() + "\n" +
                "\"Красивых\" слов (5 буквы): " + five.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean istPalindrom(String nickname) {
        int i1 = 0;
        int i2 = nickname.length() - 1;
        while (i2 > i1) {
            if (nickname.charAt(i1) != nickname.charAt(i2)) {
                return false;
            }
            ++i1;
            --i2;
        }
        return true;
    }

    public static void choiceAtomic(int length) {
        switch (length) {
            case 3:
                three.getAndIncrement();
                break;
            case 4:
                four.getAndIncrement();
                break;
            default:
                five.getAndIncrement();
                break;
        }
    }

    public static boolean isSameCharacters(String str) {
        char c = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRaise(String str) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(j) - str.charAt(i) < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
