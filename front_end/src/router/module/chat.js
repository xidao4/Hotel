const msgRouter = [
    {
        path: '/:userType/chatMessage',
        name: 'chatMessage',
        component: () => import('@/views/chat/ChatBox'),
    },

];

export default msgRouter;
