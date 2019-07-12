package javamemory;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
@Slf4j
public class TestRandom {

    public static void main(String[] args) {
        Savepoint s;
        Connection c=null;
        Random r =  //new SecureRandom();new Random();
                    ThreadLocalRandom.current();
        long val=0;
        for (;;) {
            long t0 = System.nanoTime();
            for (int i = 0; i < 1000; i++) {
                val+=new UUID(r.nextLong(), r.nextLong()).getLeastSignificantBits();
            }
            val -= val;
            System.out.println(System.nanoTime() - t0);
        }
    }
}
