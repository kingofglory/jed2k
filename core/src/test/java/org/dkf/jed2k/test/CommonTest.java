package org.dkf.jed2k.test;

import org.dkf.jed2k.Constants;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CommonTest {
    private static Logger log = Logger.getLogger(CommonTest.class.getName());
    private static byte[] data = new byte[(int)Constants.PIECE_SIZE];
    private static ByteBuffer dataBuffer = ByteBuffer.wrap(data);

    static {
        for(int i = 0; i < (int)Constants.PIECE_SIZE; ++i) {
            data[i] = 0;
        }
    }

    private ByteBuffer getBlock(int index) {
        assert(index < Constants.BLOCKS_PER_PIECE);
        dataBuffer.limit((int)(Constants.BLOCK_SIZE*(index+1)));
        dataBuffer.position((int)(index*Constants.BLOCK_SIZE));
        return dataBuffer.slice();
    }

    @Test
    public void testBufferwindows() {
        ByteBuffer piece = ByteBuffer.wrap(data);
        assertEquals((int)Constants.PIECE_SIZE, piece.limit());
        assertEquals((int)Constants.PIECE_SIZE, piece.remaining());

        ArrayList<Integer> template = new ArrayList<Integer>(Constants.BLOCKS_PER_PIECE);
        for (int i = 0; i < Constants.BLOCKS_PER_PIECE; ++i) {
            template.add(null);
        }

        template.set(4, 4);
        template.set(33, 3);
        template.set(12, 6);
        template.set(49, 1);
        template.set(0, 10);

        for(int i = 0; i < template.size(); ++i) {
            if (template.get(i) == null) continue;
            ByteBuffer block = getBlock(i);
            assertTrue(block != null);
            assertEquals((int)Constants.BLOCK_SIZE, block.remaining());
            for(int j = 0; j < (int)Constants.BLOCK_SIZE; ++j) {
                block.put((byte)template.get(i).intValue());
            }
        }

        for(int i = 0; i < (int)Constants.PIECE_SIZE; ++i) {
            assertEquals((template.get(i/(int)Constants.BLOCK_SIZE) != null)?template.get(i/(int)Constants.BLOCK_SIZE):0, data[i]);
        }
    }

    @Test
    public void testJavaNumbersConversion() {
        long original = 0xffffffffL;
        assertTrue(original > 0);
        int converted = (int)original;
        assertTrue(converted < 0);
        long converted2 = (long)(converted) & 0xffffffffL;
        assertEquals(0xffffffffL, converted2);
    }
}
