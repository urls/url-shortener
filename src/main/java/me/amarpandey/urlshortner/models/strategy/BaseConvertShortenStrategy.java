package me.amarpandey.urlshortner.models.strategy;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;

@Component
public class BaseConvertShortenStrategy implements ShortenCodeStrategy {

    private static int rowId = 0;

    @Override
    public String generateCode() {
        return baseConvert(rowId++, 10, 36);
    }

    private String baseConvert(final int input, final int fromBase, final int toBase) {
        return new BigInteger(String.valueOf((System.currentTimeMillis() + input) * new Random().nextLong()), fromBase).toString(toBase);
    }
}
