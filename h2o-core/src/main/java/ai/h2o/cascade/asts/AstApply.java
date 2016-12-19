package ai.h2o.cascade.asts;

import ai.h2o.cascade.core.Function;
import ai.h2o.cascade.vals.Val;
import water.util.SB;

import java.util.ArrayList;

/**
 */
public class AstApply extends Ast<AstApply> {
  private Ast head;
  private Ast[] args;

  public AstApply(Ast head, ArrayList<Ast> args) {
    this.head = head;
    this.args = args.toArray(new Ast[args.size()]);
  }

  @Override
  public Val exec() {
    Function f = head.exec().getFun();
    Val[] vals = new Val[args.length];
    for (int i = 0; i < vals.length; i++) {
      vals[i] = args[i].exec();
    }
    // TODO
    return null;
  }

  @Override
  public String str() {
    SB sb = new SB().p('(').p(head.str());
    for (Ast arg : args) {
      sb.p(' ').p(arg.str());
    }
    return sb.p(')').toString();
  }
}
