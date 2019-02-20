<template>
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                zNodes: [
                    { id:1, pId:0, name:"parent node 1", open:true},
                    { id:11, pId:1, name:"leaf node 1-1"},
                    { id:12, pId:1, name:"leaf node 1-2"},
                    { id:13, pId:1, name:"leaf node 1-3"},
                    { id:2, pId:0, name:"parent node 2", open:true},
                    { id:21, pId:2, name:"leaf node 2-1"},
                    { id:22, pId:2, name:"leaf node 2-2"},
                    { id:23, pId:2, name:"leaf node 2-3"},
                    { id:3, pId:0, name:"parent node 3", open:true },
                    { id:31, pId:3, name:"leaf node 3-1"},
                    { id:32, pId:3, name:"leaf node 3-2"},
                    { id:33, pId:3, name:"leaf node 3-3"}
                ],
                log: "dark",
                className: "dark",
                newCount: 1,
                setting:{
                    view: {
                        addHoverDom: this.addHoverDom,
                        removeHoverDom: this.removeHoverDom,
                        selectedMulti: false
                    },
                    edit: {
                        enable: true,
                        editNameSelectAll: true,
                        showRemoveBtn: this.showRemoveBtn,
                        showRenameBtn: this.showRenameBtn
                    },
                    data:{
                        simpleData:{
                            enable: true,
                            /*idKey: "id",
                            pIdKey: "pId",
                            rootPId: 0*/
                        }
                    },
                    callback: {
                        beforeDrop: this.beforeDrop,
                        beforeDrag: this.beforeDrag,
                        beforeEditName: this.beforeEditName,
                        beforeRemove: this.beforeRemove,
                        beforeRename: this.beforeRename,
                        onRemove: this.onRemove,
                        onRename: this.onRename
                    }
                },
            };
        },
        mounted(){
            $.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes);
        },
        methods: {
            addHoverDom(treeId, treeNode) {
                let sObj = $("#" + treeNode.tId + "_span");
                if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
                    let addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                        + "' title='add node' onfocus='this.blur();'></span>";
                    sObj.after(addStr);
                    let btn = $("#addBtn_"+treeNode.tId);
                    if (btn) btn.bind("click", function(){
                        let zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        alert(this.newCount);
                        zTree.addNodes(treeNode, {id:(100 + this.newCount), pId:treeNode.id, name:"new node" + this.newCount});
                        return false;
                    });
            },
            removeHoverDom(treeId, treeNode) {
                $("#addBtn_"+treeNode.tId).unbind().remove();
            },
            // 显示删除按钮
            showRemoveBtn(treeId, treeNode) {
                return !treeNode.isFirstNode;
            },
            // 显示修改按钮
            showRenameBtn(treeId, treeNode) {
                return !treeNode.isLastNode;
            },
            beforeDrop(treeId, treeNodes, targetNode, moveType) {
                return targetNode ? targetNode.drop !== false : true;
            },
            beforeDrag(treeId, treeNodes) {
                for (let i=0,l=treeNodes.length; i<l; i++) {
                    if (treeNodes[i].drag === false) {
                        return false;
                    }
                }
                return true;
            },
            beforeEditName(treeId, treeNode) {
                this.className = (this.className === "dark" ? "":"dark");
                this.showLog("[ "+this.getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
                let zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.selectNode(treeNode);
                setTimeout(function() {
                    if (confirm("Start node '" + treeNode.name + "' editorial status?")) {
                        setTimeout(function() {
                            zTree.editName(treeNode);
                        }, 0);
                    }
                }, 0);
                return false;
            },
            beforeRemove(treeId, treeNode) {
                this.className = (this.className === "dark" ? "":"dark");
                this.showLog("[ "+this.getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
                let zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.selectNode(treeNode);
                return confirm("是否删除节点" + treeNode.name + "?");
            },
            beforeRename(treeId, treeNode, newName, isCancel) {
                this.className = (this.className === "dark" ? "":"dark");
                this.showLog((isCancel ? "<span style='color:red'>":"") + "[ "+this.getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
                if (newName.length == 0) {
                    setTimeout(function() {
                        let zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        zTree.cancelEditName();
                        alert("内容不能为空");
                    }, 0);
                    return false;
                }
                return true;
            },
            onRemove(e, treeId, treeNode) {
                this.showLog("[ "+this.getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
            },
            onRename(e, treeId, treeNode, isCancel) {
                this.showLog((isCancel ? "<span style='color:red'>":"") + "[ "+this.getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
            },
            showLog(str) {
                if (this.log) {
                    this.log = $("#log");
                }
                this.log.append("<li class='"+this.className+"'>"+str+"</li>");
                if(this.log.children("li").length > 8) {
                    this.log.get(0).removeChild(this.log.children("li")[0]);
                }
            },
            getTime() {
                let now= new Date(),
                    h=now.getHours(),
                    m=now.getMinutes(),
                    s=now.getSeconds(),
                    ms=now.getMilliseconds();
                return (h+":"+m+":"+s+ " " +ms);
            }
        },
    };
</script>
<style lang="scss">
</style>
