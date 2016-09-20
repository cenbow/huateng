module.exports = {
    settings: {
        configPath: "sys/style/view",
        loadConfig: false
    },

    init: function() {
        var mouseController = require("design/interactions/mouse_controller")
        mouseController.droppable(this);
    }
}
