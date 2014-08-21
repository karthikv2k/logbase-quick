LB.IndexController = Ember.Controller.extend({
    searchTerm: '',
    actions: {
        goToWorkspace: function() {
            console.log('hang on I"m going to workspace for search term' + this.get('searchTerm'));
            //Store this at application level for the workspace to render
            LB.ApplicationController.lastSearchTerm = this.get('searchTerm');
            //Go to default workspace
            this.transitionToRoute('workspace',0);
        }
    }
});