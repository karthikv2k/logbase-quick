LB.WorkspaceController = Ember.Controller.extend({
    searchTerm: function(){
        console.log('Getting search term from App ' + LB.ApplicationController.lastSearchTerm);
        return LB.ApplicationController.lastSearchTerm;
    }.property(),
    actions: {
        fireQuery: function() {
            console.log('hang on I"m firing query for ' + this.get('searchTerm'));
            //TODO fetch results
        }
    }
});