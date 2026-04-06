SKSafety {
    var <server;
    var <limiterSynth;
    var <masterBus;
    var <enabled = true;
    var <maxLevel = 0.95;

    *new { |server, masterBus|
        ^super.new.init(server ? Server.default, masterBus)
    }

    init { |aServer, aMasterBus|
        server = aServer;
        masterBus = aMasterBus;
        ^this
    }

    install {
        enabled.if {
            "SKSafety: limiter install not implemented yet".postln;
        };
        ^this
    }

    uninstall {
        limiterSynth.notNil.if(_.free);
        limiterSynth = nil;
        ^this
    }

    panic {
        server.freeAll;
        ^this
    }

    maxLevel_ { |value|
        maxLevel = value.clip(0.1, 1.0);
        ^maxLevel
    }
}