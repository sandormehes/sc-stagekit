Mixer {
    var <server;
    var <masterBus;
    var <trackBuses;
    var <fxNodes;
    var <masterGroup;
    var <fxGroup;

    *new { |server|
        ^super.new.init(server ? Server.default)
    }

    init { |aServer|
        server = aServer;
        trackBuses = IdentityDictionary.new;
        fxNodes = IdentityDictionary.new;
        ^this
    }

    boot {
        masterBus = Bus.audio(server, 2);
        masterGroup = Group.head(server);
        fxGroup = Group.after(masterGroup);
        ^this
    }

    ensureTrackBus { |trackName, channels = 2|
        var bus = trackBuses[trackName];
        bus.isNil.if {
            bus = Bus.audio(server, channels);
            trackBuses[trackName] = bus;
        };
        ^bus
    }

    trackBus { |trackName|
        ^trackBuses[trackName]
    }

    applyFXConfig { |config|
        "SKMixer: applyFXConfig not implemented yet".postln;
        ^config
    }

    setMasterLevel { |value|
        "SKMixer: setMasterLevel not implemented yet".postln;
        ^value
    }
}