package Atomix;

import java.util.HashMap;
import java.util.Map;

import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.StateMachine;

public class MapStateMachine extends StateMachine {

    private Map<Object, Object> map = new HashMap<Object, Object>();

    public Object put(Commit<PutCommand> commit) {
        try {
          map.put(commit.operation().key(), commit.operation().value());
        } finally {
          commit.close();
        }
		return commit;
      }

      public Object get(Commit<GetQuery> commit) {
        try {
          return map.get(commit.operation().key());
        } finally {
          commit.close();
        }
      }

}
