package com.osu.dp.string_matching.LevenshteinAutomaton;

import java.util.BitSet;

/** Pomocná třída pro překládání do bit-vektorové reprezentace. */
public class BitVector extends BitSet {
    private int BitSetSize = 0;

    public BitVector(int BitSetSize) {
        super(BitSetSize);
        this.BitSetSize = BitSetSize; //inicializace požadované velikosti bit-vektoru

    }

    private BitVector(BitSet bs)
    {
        super();
        super.xor(bs);
    }

    @Override
    public BitVector get(int fromIndex, int toIndex) {
        return new BitVector(super.get(fromIndex, toIndex));
    }

    public int getBitSetSize() {
        return BitSetSize;
    }
}