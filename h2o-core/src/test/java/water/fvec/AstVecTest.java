package water.fvec;

import org.junit.BeforeClass;
import org.junit.Test;
import water.MRTask;
import water.TestUtil;
import water.rapids.ast.AstRoot;
import water.rapids.Rapids;

public class AstVecTest extends TestUtil {
  @BeforeClass static public void setup() {  stall_till_cloudsize(1); }

  @Test public void testInversion() {
    Vec v=null;
    try {
      v = Vec.makeZero(1<<20);
      AstRoot ast = Rapids.parse("{ x . (- 1 x) }");
      Vec iv = new AstVec(v, ast);
      new MRTask() {
        @Override public void map(Chunk c) {
          for(int i=0;i<c._len;++i)
            if (c.atd(i) != 1.0)
              throw new RuntimeException("moo");
        }
      }.doAll(iv);
      iv.remove();
    } finally {
      if( null!=v ) v.remove();
    }
  }
}