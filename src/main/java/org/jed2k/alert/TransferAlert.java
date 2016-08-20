package org.jed2k.alert;

import org.jed2k.protocol.Hash;

/**
 * Created by inkpot on 18.08.2016.
 */
public class TransferAlert extends Alert {
    public final Hash hash;

    public TransferAlert(final Hash h) {
        hash = h;
    }

    @Override
    public Severity severity() {
        return null;
    }

    @Override
    public int category() {
        return 0;
    }
}