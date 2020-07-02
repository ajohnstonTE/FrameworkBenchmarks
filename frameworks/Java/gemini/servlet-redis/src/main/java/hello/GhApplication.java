package hello;

import com.techempower.data.*;
import com.techempower.data.jdbc.*;
import com.techempower.gemini.*;
import com.techempower.gemini.exceptionhandler.*;
import com.techempower.gemini.path.*;

import hello.home.handler.*;

/**
 * GeminiHello Application.  As a subclass of GeminiApplication, this
 * class acts as the central "hub" of references to components such as
 * the Dispatcher and EntityStore.
 *
 * Development history:
 *   2012-04-19 - mh - Created
 *   2020-04-17 - ms - Updated to Gemini 3.0.2
 *
 * @author mhixson
 */
public class GhApplication
     extends ResinGeminiApplication
{
  private Initialisable initialisable;

  private boolean initialized;

  @Override
  public void initialize(InitConfig config)
  {
    // Resin calls this method twice if told to load on startup.
    if (!initialized)
    {
      synchronized (this)
      {
        if (!initialized)
        {
          initialized = true;

          initialisable.initialize();

          super.initialize(config);
        }
      }
    }
  }

  /**
   * Constructs a Dispatcher.
   */
  @SuppressWarnings("unchecked")
  @Override
  protected Dispatcher constructDispatcher()
  {
    final PathDispatcher.Configuration<Context> config = new PathDispatcher.Configuration<>();

    String redisClient = System.getenv("REDIS_CLIENT");

    if (redisClient.equals("REDISSON")) {
      initialisable = new RedissonHelloHandler(this);
    } else if (redisClient.equals("JEDIS")) {
      initialisable = new JedisHelloHandler(this);
    } else {
      System.out.println("Failed to determine redis client");
      System.exit(1);
    }

    config.setDefault((PathHandler<Context>) initialisable)
          .add(new BasicExceptionHandler(this));

    return new PathDispatcher<>(this, config);
  }

  @Override
  protected ConnectorFactory constructConnectorFactory()
  {
    return new BasicConnectorFactory(this, null);
  }

}
