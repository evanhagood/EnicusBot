package com.enicus.enicusbot;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
/* IMPORTS */

/* devi8tion imports */
import com.enicus.enicusbot.listeners.EventListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
/* env file */
import io.github.cdimascio.dotenv.Dotenv;
/* javax */
import javax.security.auth.login.LoginException;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
/* CLASS */
public class EnicusBot {
    /* INSTANCE VARIABLES */
    private final ShardManager shardManager;
    private final Dotenv config;

    /* CONSTRUCTOR(S) */
    public EnicusBot() throws LoginException {
        config = Dotenv.configure().ignoreIfMissing().load(); // will ignore if env file is missing
        String token = config.get("TOKEN");
        /* a shard allows the bot to run on multiple instances */
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.enableIntents(
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS
        );
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Lethal Company"));
        /* be careful! this will cache ALL members in ALL servers .. */
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ROLE_TAGS);
        shardManager = builder.build();

        // register listeners
        shardManager.addEventListener(new EventListener());
    }

    /* MAIN METHOD */
    public static void main(String[] args) {
        // Sharding method for bot usage on more than one server :)
        try {
            EnicusBot bot = new EnicusBot();
        } catch (LoginException e) {
            System.out.println("Error: Provided bot token is invalid.");
        }
    }

    /* GETTERS AND SETTERS */
    public Dotenv getConfig() {
        return config;
    }
    public ShardManager getShardManager() {
        return shardManager;
    }
}
