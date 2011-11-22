
//***********************
//** Generic Tree Node **
//***********************
function NBNode( item ) {
    this.id = item.id;
    this.name = item.name;
    this.parentId = item.parentId;
    this.children = null;
} 

NBNode.prototype = {
    addChild: function( node ) {
    	if( this.children == null )
    		this.children = {};
		this.children[node.id] = node;
    }
}
		

//******************
//** Generic Tree **
//******************
function NBTree() {
	this.root = null;
}

NBTree.prototype = {
	buildFromFlatData : function( flatData ) {
		var nodes={};
		for( var i in flatData ) {
			if(!flatData.hasOwnProperty(i))
				continue;
			var item = flatData[i];
			item['id'] = i;
			nodes[i] = new NBNode(item);
		}
		
		for( var i in nodes ) {
			if(!nodes.hasOwnProperty(i))
				continue;
		    var node = nodes[i];
            if ( node.parentId != null && node.parentId.length != undefined  && !(node.parentId.length==1 && node.parentId[0]==null) ) {
                for (var i=0 ; i<node.parentId.length ; i++ ) {
                    var parentId = node.parentId[i];
                    if( !nodes[parentId] )
                        alert( 'Tree data is inconsistent: node with the id=' + node.id + ' points to the parent that doesn\'t exist (parentId=' + parentId + ')');
                    var parent =  nodes[parentId];
                    parent.addChild(node);
                }
		    } else {
		    	if( this.root != null )
		    		alert( 'Tree data is inconsistent: node with the id=' + node.id + ' and id=' + this.root.id + ' both have parents=null');
				this.root = node;
		    }
		}
	},

	generateJstreeJsonData : function() {
		var go = function(node, compositeId) {
			compositeId += '_'+node.id;
			var jsNode = { 
				'attr' : { 
					'id' : 'ptid'+compositeId,
					'title' : 'Click to add this item to your selections for the search'
				},
				'data' : {
					'title' : node.name,
					'icon' : 'folder'
				}, 
				'metadata' : { 'id' : compositeId },
			};
			if( node.children != null ) {
				//jsNode['state'] = 'closed'; 	
				jsNode['children'] = [];
				jQuery.map(node.children, function(val,i) {
					jsNode.children.push(go(val,compositeId));  
				});
			}
			return jsNode;
		};
		
		return { 
			'data' : [go(this.root,'')],
			'progressive_render' : false 
		};
	},
	
    add : function(id, name, parentId){
    },

    contains : function(value){
    },
    
    remove : function(value){
    },

    size : function(){
    },
    
    toArray : function(){
    },
    
    toString : function(){
        return this.toArray().toString();
    },
    
    traverse: function(process){
    }
};